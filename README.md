KASL (Kotlin Advanced Service Locator)
----

## About

Small service locator for kotlin.

**Explicit Dependency is usually better than Implicit Dependency**

Martin Fowler said:
> Inversion of control is a common feature of frameworks, but it's something that comes at a price. It tends to be hard to understand and leads to problems when you are trying to debug. So on the whole I prefer to avoid it unless I need it. 

https://www.martinfowler.com/articles/injection.html

It might be better to use DI in some special cases. But usually it waste resources. ServiceLocator is simple and enough solution.

## Example

You can replace instance without paying cost to learn mock library.

```kotlin
import com.lavans.kasl.Locator
class UserService(){
  private val userRepo = Locator.get(UserRepo::class)
  fun get(id: Int) = userRepo.find(id)
}

fun main(){
  Locator.set(UserRepo::class, MockedUserRepo())
  UserService().get(1) // ... MockedUserRepo.find() will be called
}
```

Just write mock class that you want to do.

```kotlin
class MockedUserRepo(): UserRepo(){
  override fun find(id: Int) = 1
}
```

Not-overrided functions are still valid since it's derived from real UserRepo.

Happy Hacking!
