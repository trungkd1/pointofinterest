![Alt text](screenshots/playstore.jpg?raw=true "Banner")

## Introduce Application (Points of interest)
==================


## English Introduce


This application allows users to easily receive and organize shared data, such as links, texts, and images via website links.
Create your favorites effortlessly with a minimalistic interface:
  - To utilize a wizard to automatically analyze link content
  - To make your own custom categories
The application also allows for easy searching and filtering of stored information, enabling you to quickly find the information you need.

Furthermore, this application utilizes the latest technologies such as Dagger Hilt, Jetpack Compose, and is coded in the Kotlin language.

## Architecture

This application is divided into 2 layers: Data and UI.
  - Data layer: To make databases and connect to servers, interacting with the UI layer through an interactor.
  - UI layer: Where the interface is contained and handles the display logic on the screen. The app uses the MVVM model to create the structure at the UI layer. UI is the View (UI), Model is used to transmit data between UI classes), and ViewModel is the part of handling logic on the UI screen).

### :data (Data layer)

 3rd party libraries and technologies used:
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) To use and manage dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) To load asynchronous data
  - [Room](https://developer.android.com/training/data-storage/room) This is a library used to manipulate databases
  - [Retrofit](https://square.github.io/retrofit/) Use to request HTTP and read the data form such as Json XML và ProtoBuf
  - [OkHttp](https://square.github.io/okhttp/) Use to hook up API server
  - [Jsoup](https://jsoup.org/) Convert String into an Object with the form of Document to access String's traits

 Features:
  - [Database](/data/src/main/java/com/trungkieu/data/database) Create and connect Database
  - [DI Modules](/data/src/main/java/com/trungkieu/data/di) Execute classes with module annotations to create instances
  - [Interactor](/data/src/main/java/com/trungkieu/data/features/poi/interactor) Communicate to UI layer
  - [Data level models](/data/src/main/java/com/trungkieu/data/features/poi/model) The models are used to transmit data in data layer
  - [DAO](/data/src/main/java/com/trungkieu/data/features/poi/dao) Write SQL statements
  - [Data sources](/data/src/main/java/com/trungkieu/data/features/poi/datasource) Call interfaces with DAO annotations to execute SQL statements
  - [Network calls](/data/src/main/java/com/trungkieu/data/features/poi/api) Connect network

### :app (UI layer)

3rd party libraries and technologies used:
  - [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAoL6eBhA3EiwAXDom5uovlfrS1-2xp88b8zKsFzkiW36VKaFC01x9UM7zCvrIpCnRptZGJhoCq90QAvD_BwE&gclsrc=aw.ds) Use compose to create UI instead of being XML
  - [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAiAoL6eBhA3EiwAXDom5oKABL8-HMrHV2XjQTCwKqtV-iMS4fTKJwgFsJDnzSwuNmDy0vEHyxoCqwkQAvD_BwE&gclsrc=aw.ds0) Use to handle the logic of UI and connect with data layer
  - [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) : Use to navigate screens
  - [Splash Screen Api](https://developer.android.com/develop/ui/views/launch/splash-screen) Product intro screen ahead of entering the main screen
  - [Coil for Compose](https://coil-kt.github.io/coil/compose/) Use to load image
  - [Flow layout](https://google.github.io/accompanist/flowlayout/) This is a layout manager of Android used to arrange views from left to right, top to bottom
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Use to create and manage dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) Support asynchronous processings without crashing the application, and is used in conjunction with coroutines




## Vietnamese Introduce

Ứng dụng này cho phép người dùng dễ dàng nhận và sắp xếp dữ liệu được chia sẻ, chẳng hạn như liên kết, văn bản và hình ảnh thông qua link website
Tạo các điểm ưa thích của bạn một cách dễ dàng với giao diện tối giản:
  - Sử dụng wizard để tự động phân tích nội dung liên kết
  - Tạo danh mục tùy chỉnh của riêng bạn
  Ứng dụng còn cho phép dễ dàng tìm kiếm và lọc các thông tin được lưu trữ, giúp bạn dễ dàng tìm thấy thông tin mình cần một cách nhanh chóng.

Ngoài ra, Ứng dụng này được sử dụng các công nghệ mới nhất như : Dagger hilt, Jetpack Compose và được code bằng ngôn ngữ Kotlin.

## Architecture
Ứng dụng này được chia làm 2 layer là Data và UI
  - Data layer: Dùng để tạo database và kết nối server và tương tác với tầng UI thông qua interactor
  - UI layer: Nơi chứa giao diện và xử lý các logic hiển thị trên màn hình. Sử dụng mô hình MVVM để tạo cấu trúc ở tầng UI. Trong đò, View (IU), Model (dữ liệu được truyền tải giữa các class Ui và giao tiếp với tầng Data), ViewModel (phần xử lý logic trên màn hình UI)

### :data (Data layer)

 3rd party libraries and technologies used:
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Dùng để tạo và quản lý các dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) Dùng để load dữ liệu bất đồng bộ
  - [Room](https://developer.android.com/training/data-storage/room) Là một thư viện dùng để thao tác CSDL
  - [Retrofit](https://square.github.io/retrofit/) Dùng để request HTTP và đọc các định dạng dữ liệu Json XML và ProtoBuf
  - [OkHttp](https://square.github.io/okhttp/) Dùng cho việc kết nối API server
  - [Jsoup](https://jsoup.org/) Chuyển đổi chuỗi thành một đối tượng đạng Document để truy cập các thuộc tính của chuỗi

 Features:
  - [Database](/data/src/main/java/com/trungkieu/data/database) Tạo và kết nối database
  - [DI Modules](/data/src/main/java/com/trungkieu/data/di) Thực hiện các class có annotation kiểu module để tạo các instances
  - [Interactor](/data/src/main/java/com/trungkieu/data/features/poi/interactor) Giao tiếp với tầng UI
  - [Data level models](/data/src/main/java/com/trungkieu/data/features/poi/model) Các kiểu model dùng để truyền dữ liệu trong tầng data
  - [DAO](/data/src/main/java/com/trungkieu/data/features/poi/dao) Viết các câu lệnh SQL
  - [Data sources](/data/src/main/java/com/trungkieu/data/features/poi/datasource) Gọi các interface có annotation DAO để thực thi câu lệnh SQL
  - [Network calls](/data/src/main/java/com/trungkieu/data/features/poi/api) Kết nối network

### :app (UI layer)

3rd party libraries and technologies used:
  - [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAoL6eBhA3EiwAXDom5uovlfrS1-2xp88b8zKsFzkiW36VKaFC01x9UM7zCvrIpCnRptZGJhoCq90QAvD_BwE&gclsrc=aw.ds) Tạo giao diện bằng compose thay vì XML
  - [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAiAoL6eBhA3EiwAXDom5oKABL8-HMrHV2XjQTCwKqtV-iMS4fTKJwgFsJDnzSwuNmDy0vEHyxoCqwkQAvD_BwE&gclsrc=aw.ds0) Dùng để xử lý phần logic của UI và kết nối với tầng data
  - [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) : dùng để điều hướng các màn hình
  - [Splash Screen Api](https://developer.android.com/develop/ui/views/launch/splash-screen) màn hình hiển thị sản phẩm trước khi vào màn hình chính
  - [Coil for Compose](https://coil-kt.github.io/coil/compose/) dùng để load hình ảnh
  - [Flow layout](https://google.github.io/accompanist/flowlayout/) là layout manager trong Android được sử dụng để sắp xếp các view từ trài qua phải từ trên xuống dưới.
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Dùng để tạo và quản lý các dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) hỗ trợ xử lý bất đồng bộ mà không làm treo ứng dụng, và được dùng kết hợp với coroutine 

![Alt text](screenshots/flow.png?raw=true "Banner")
