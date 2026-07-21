# 🎙️ Talkie Social - AI-Powered Voice Social Network

**Talkie Social** là một nền tảng mạng xã hội hiện đại tập trung vào trải nghiệm âm thanh và tương tác cộng hưởng (Resonance). Dự án được xây dựng với mục tiêu thể hiện kỹ năng lập trình Android chuyên nghiệp thông qua kiến trúc module hóa và các công nghệ mới nhất trong hệ sinh thái Android.

---

## 🚀 Công nghệ sử dụng (Tech Stack)

Dự án áp dụng bộ công nghệ "Modern Android Development" (MAD):

*   **Ngôn ngữ**: Kotlin (100%) - Tận dụng Coroutines & Flow cho xử lý bất đồng bộ.
*   **UI Framework**: Jetpack Compose - Declarative UI hiện đại.
*   **Kiến trúc**: Clean Architecture + MVVM + MVI (Effect-based navigation).
*   **Dependency Injection**: Hilt (Dagger) - Quản lý phụ thuộc hiệu quả.
*   **Networking**: Supabase SDK (Ktor/OkHttp) - Kết nối Backend real-time.
*   **Database**: Room Persistence - Lưu trữ dữ liệu cục bộ.
*   **Image Loading**: Coil - Tải ảnh tối ưu cho Compose.
*   **Build System**: Gradle Kotlin DSL + Version Catalog (libs.versions.toml) - Đồng bộ hóa dependency chuẩn Google.

---

## 🏗 Kiến trúc dự án (Architecture)

Dự án được chia thành các **Module** để tăng khả năng bảo trì, tái sử dụng và tốc độ build:

### 📂 Cấu trúc Module:
*   `:app`: Module chính khởi chạy ứng dụng, quản lý Navigation tổng và Dependency Injection tầng cuối.
*   `:core:ui`: Chứa **Design System (Electric Pulse v2)**, các Component dùng chung (PulseCard, Modifiers, Buttons) và Theme.
*   `:core:network`: Cấu hình kết nối API, Supabase Client và các Interceptor.
*   `:core:common`: Chứa các tiện ích, Base class (BaseViewModel, BaseScreen) và định nghĩa Navigation Routes.
*   `:data`: Hiện thực hóa các Repository, quản lý luồng dữ liệu giữa API và Local Database.
*   `:domain`: Tầng chứa Business Logic (UseCases, Models, Repository Interfaces) - Hoàn toàn không phụ thuộc vào Android Framework.
*   `:feature:auth`: Module tính năng Đăng nhập/Đăng ký.
*   `:feature:home`: Module tính năng chính (Feed, Explore, Chat, Profile).

---

## ✨ Kỹ thuật nổi bật (Highlights)

### 1. Design System tùy chỉnh (Electric Pulse v2)
Không sử dụng các giao diện đại trà, dự án sở hữu một bộ nhận diện riêng:
*   **Custom Modifier (`neonGlow`)**: Tự vẽ lớp bóng mờ (shadow layer) ở tầng Canvas để tạo hiệu ứng phát sáng Neon độc đáo.
*   **Glassmorphism UI**: Sử dụng hiệu ứng kính mờ và Gradient để tạo chiều sâu cho giao diện.
*   **Dynamic Waveform**: Trình mô phỏng sóng âm chuyển động mượt mà cho các bài đăng âm thanh.

### 2. Xử lý Logic Auth thông minh
*   **Lifecycle Awareness**: Tích hợp chặt chẽ với `sessionStatus` của Supabase để tự động duy trì trạng thái đăng nhập khi người dùng mở lại app.
*   **Polymorphic Effects**: Sử dụng Interface để quản lý các hiệu ứng UI (như Snackbar thông báo lỗi) một cách tập trung tại `BaseScreen`, giúp giảm 40% lượng code lặp lại ở tầng UI.

### 3. Clean Architecture & SOLID
*   Tách biệt hoàn toàn `Route` (Navigation/Logic) và `Screen` (Stateless UI).
*   **Dependency Inversion**: Tầng `data` phụ thuộc vào `interface` ở tầng `domain`, giúp việc thay đổi backend (như từ Retrofit sang Supabase) diễn ra cực kỳ nhanh chóng và an toàn.

### 4. Quản lý Dependency chuyên nghiệp
*   Sử dụng **Gradle Version Catalog** để quản lý hàng chục thư viện một cách tập trung, tránh xung đột phiên bản và tối ưu hóa thời gian đồng bộ dự án.

---

## 🛠 Cài đặt và Chạy thử
1. Clone dự án.
2. Mở file `core/network/build.gradle.kts` và điền `SUPABASE_URL` / `SUPABASE_KEY` của bạn.
3. Build và chạy trên thiết bị Android (API 24+).

---

**Talkie Social** không chỉ là một ứng dụng, mà là minh chứng cho tư duy viết code sạch, kiến trúc bền vững và niềm đam mê với trải nghiệm người dùng đỉnh cao.
