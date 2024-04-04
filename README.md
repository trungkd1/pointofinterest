![Alt text](screenshots/playstore.jpg?raw=true "Banner")

## Introduce Application (Points of interest)
==================

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
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) // để load dữ liệu bất đồng bộ
  - [Room](https://developer.android.com/training/data-storage/room) lưu trữ database
  - [Retrofit](https://square.github.io/retrofit/) dùng để request HTTP và đọc các định dạng dữ liệu Json XML và ProtoBuf
  - [OkHttp](https://square.github.io/okhttp/) dùng cho việc kết nối API server
  - [Jsoup](https://jsoup.org/)

 Features:
  - [Database](/data/src/main/java/com/trungkieu/data/database) // Tạo và kết nối database
  - [DI Modules](/data/src/main/java/com/trungkieu/data/di) Thực hiện các class có annotation kiểu module để taopj các dependency injection
  - [Interactor](/data/src/main/java/com/trungkieu/data/features/poi/interactor) giao tiếp với tầng UI
  - [Data level models](/data/src/main/java/com/trungkieu/data/features/poi/model) các kiểu model dùng để truyền dữ liệu trong tầng data
  - [DAO](/data/src/main/java/com/trungkieu/data/features/poi/dao) : Viết các câu lệnh SQL
  - [Data sources](/data/src/main/java/com/trungkieu/data/features/poi/datasource) Gọi các interface có annotation DAO để thực thi câu lệnh SQL
  - [Network calls](/data/src/main/java/com/trungkieu/data/features/poi/api) : Kết nối network

### :app (UI layer)

3rd party libraries and technologies used:
  - [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAoL6eBhA3EiwAXDom5uovlfrS1-2xp88b8zKsFzkiW36VKaFC01x9UM7zCvrIpCnRptZGJhoCq90QAvD_BwE&gclsrc=aw.ds) Tạo giao diện bằng compose thay vì XML
  - [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAiAoL6eBhA3EiwAXDom5oKABL8-HMrHV2XjQTCwKqtV-iMS4fTKJwgFsJDnzSwuNmDy0vEHyxoCqwkQAvD_BwE&gclsrc=aw.ds0) Dùng để xử lý phần logic của UI và kết nối với tầng data
  - [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) : dùng để điều hướng các màn hình
  - [Splash Screen Api](https://developer.android.com/develop/ui/views/launch/splash-screen) màn hình hiển thị sản phẩm trước khi vào màn hình chính
  - [Coil for Compose](https://coil-kt.github.io/coil/compose/) dùng để load hình ảnh
  - [Flow layout](https://google.github.io/accompanist/flowlayout/) là layout manager trong Android được sử dụng để sắp xếp các view từ trài qua phải từ trên xuống dưới.
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) dùng để xử lý các luồng dữ liệu bất đồng bộ. 

