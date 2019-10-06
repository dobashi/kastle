KASL (Kotlin Advanced Service Locator)
----

## About

Small service locator for kotlin.

**Explicit Dependency is usually better than Implicit Dependency**

## Example

Just call `Locatro.get(Class<T>)`, you'll get instance.

```kotlin
import com.lavans.kasl.Locator
class UserService(){
  private val userRepo = Locator.get(UserRepo::class)
  fun get(id: Int) = userRepo.find(id)
}
```

### Replace with Mock class

You can replace instance without paying cost to learn mock library.

```kotlin
fun main(){
  Locator.set(UserRepo::class, MockedUserRepo::class)
  UserService().get(1) // ... MockedUserRepo.find() will be called
}
```

Just write mock class that you want to do.

```kotlin
class MockedUserRepo(): UserRepo(){
  override fun find(id: Int) = DUMMY_USER
}
```

Not-overrided functions are still valid since it's derived from real UserRepo.

### Singleton or Prototype

Default is `Singleton`. If you want to use class as prototype, just add 1 parameter.

```kotlin
  Locator.set(Real::class, Type.Prototype)
```

You can set both Mock and Type at the same time.

```kotlin
  Locator.set(Real::class, Mocked::class, Type.Prototype)
```

### Confirm current settings

You can see current setting like this:

```kotlin
log.debug(Locator.list())
```

## Customize for Aspect

You can customize your instance dynamically with [cglib](https://github.com/cglib/cglib).

For example, If you want to manage DB transaction from services. See below:

```kotlin
object TransactionalServiceLocator {
  fun <T>get(klass: Class<T>){
    if(Locator.has(klass)){
      return Locator.get(klass)
    }
    val service = createService(klass, TransactionInterceptor())
    Locator.set(klass, service)
    return (T)service
  }

  fun <T>create(klass: Class<T>, callback: Callback): T {
    val enhancer = Enhancer()
    enhancer.setSuperclass(klass)
    enhancer.setCallback(callback)
    return (T)enhancer.create()
  }
}
```

Implement Interceptor using cglib's MethodInterceptor

```kotlin
import net.sf.cglib.proxy.MethodInterceptor

class TransactionInterceptor: MethodInterceptor{
  override fun intercept(target: Any, method: Method, args: Array<Any>, proxy: MethodProxy) {
    startTransaction();
    try{
      result = proxy.invokeSuper(obj, args);
      commit();
      return result;
    }catch(e: Exception){
      rollback();
      throw e;
    }
  }
  private fun startTransaction() = // implement your db's transaction
  private fun commit() = // ...
  private fun rollback() = // ...
}

```

### Service Locator vs DI container

Martin Fowler said:
> Inversion of control is a common feature of frameworks, but it's something that comes at a price. It tends to be hard to understand and leads to problems when you are trying to debug. So on the whole I prefer to avoid it unless I need it. 

https://www.martinfowler.com/articles/injection.html

It might be better to use DI in some special cases. But usually it waste time and effort resources. ServiceLocator is simple and enough solution.

----

Happy Hacking!


