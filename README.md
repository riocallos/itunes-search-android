# iTunes Android
Demonstrate master detail app using Kotlin, MVVM, Retrofit, Room, RxJava, Dagger, and Data Binding.

## Screenshots
**Phone**
![Phone](./app/screenshots/phone1.png)
<img src="./app/screenshots/phone1.png" width="100" height="200">
![Phone](./app/screenshots/phone2.png)
<img src="./app/screenshots/phone2.png" width="100" height="200">

**Tablet**
![Tablet](./app/screenshots/tablet.png)
<img src="./app/screenshots/tablet.png" width="200" height="100">

## Build
[Download APK here.](https://drive.google.com/file/d/1sbjno5FPQKF-d98zbwg7jC9nZkWsgzZG/view?usp=sharing)

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
