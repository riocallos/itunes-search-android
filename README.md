# iTunes Android
Demonstrate master detail app using Kotlin, MVVM, Retrofit, Room, RxJava, Dagger, and Data Binding.

## Screenshots
**Phone**
![Phone](https://drive.google.com/file/d/1F5LvbqX9SiUYDMR1H3hFhNKlS_fdwKjg/view?usp=sharing)
![Phone](https://drive.google.com/file/d/1KlLMkcDdB0MXyK7GHJD3ESQGV23RbEy3/view?usp=sharing)

**Tablet**
![Tablet](https://drive.google.com/file/d/1FtOKCaR4sFTYb62zOiNLgL9zDD3Ftvd1/view?usp=sharing)

## APK
[Download here.](https://drive.google.com/file/d/1sbjno5FPQKF-d98zbwg7jC9nZkWsgzZG/view?usp=sharing)

## Persistence
I used **Room** persistence library since it is easier to code, 
less prone to runtime error since it validates at
compile time, and works well with Rx for live data observation
as compared to SQLite.

## Architecture
I used **Model-View-ViewModel (MVVM)** since I am able to deliver
app functionality while writing significantly less code
especially when using it with Kotlin, Rx, and Android data binding.
MVVM allows the separation of view from business logic that 
makes the code easily testable, maintainable, and sustainable
which is especially helpful when multiple developers are working
on a single project and when adding new features.
