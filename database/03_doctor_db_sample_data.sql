-- ========================================================
-- LAB 03: DỮ LIỆU MẪU CHO DOCTOR SERVICE (MySQL - doctor_db)
-- Đề tài: Quản lý phòng khám và quầy thuốc
-- Đảm bảo chuẩn hóa tiếng Việt có dấu đầy đủ (utf8mb4)
-- ========================================================

USE `doctor_db`;
SET NAMES 'utf8mb4';

-- 1. Bảng doctors (Danh sách bác sĩ chuyên khoa có dấu tiếng Việt)
INSERT INTO `doctors` (`id`, `full_name`, `specialty`, `phone_number`, `email`, `consultation_fee`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'BS.CKII Trần Văn Minh', 'Khoa Tim Mạch', '0903111222', 'minh.tran@clinic.com', 300000.00, 1, NOW(), NOW()),
(2, 'ThS.BS Lê Thị Lan', 'Khoa Da Liễu', '0903222333', 'lan.le@clinic.com', 250000.00, 1, NOW(), NOW()),
(3, 'BS.CKI Phạm Quốc Huy', 'Khoa Tiêu Hóa', '0903333444', 'huy.pham@clinic.com', 280000.00, 1, NOW(), NOW()),
(4, 'BS.CKII Hoàng Ngọc Mai', 'Khoa Sản Phụ', '0903444555', 'mai.hoang@clinic.com', 350000.00, 1, NOW(), NOW()),
(5, 'ThS.BS Nguyễn Văn Đạt', 'Khoa Tai Mũi Họng', '0903555666', 'dat.nguyen@clinic.com', 250000.00, 1, NOW(), NOW()),
(6, 'BS.CKI Vũ Thị Thảo', 'Khoa Hô Hấp', '0903666777', 'thao.vu@clinic.com', 260000.00, 1, NOW(), NOW()),
(7, 'PGS.TS Lê Quốc Cường', 'Khoa Nội Tiết', '0903777888', 'cuong.le@clinic.com', 400000.00, 1, NOW(), NOW()),
(8, 'ThS.BS Đoàn Văn Nam', 'Khoa Thần Kinh', '0903888999', 'nam.doan@clinic.com', 320000.00, 1, NOW(), NOW()),
(9, 'BS.CKI Bùi Thị Thu', 'Khoa Nhi', '0903999000', 'thu.bui@clinic.com', 250000.00, 1, NOW(), NOW()),
(10, 'BS.CKII Ngô Quang Hải', 'Khoa Chấn Thương Chỉnh Hình', '0903000111', 'hai.ngo@clinic.com', 300000.00, 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `full_name` = VALUES(`full_name`),
    `specialty` = VALUES(`specialty`);

-- 2. Bảng doctor_schedules (Lịch khám bệnh của bác sĩ)
INSERT INTO `doctor_schedules` (`id`, `doctor_id`, `work_date`, `shift`, `start_time`, `end_time`, `max_patients`, `booked_patients`, `status`, `room_number`, `created_at`, `updated_at`) VALUES
(1, 1, '2026-12-01', 'SÁNG', '08:00:00', '12:00:00', 20, 5, 'AVAILABLE', 'P.201 - Lầu 2', NOW(), NOW()),
(2, 2, '2026-12-01', 'CHIỀU', '13:30:00', '17:00:00', 15, 3, 'AVAILABLE', 'P.105 - Lầu 1', NOW(), NOW()),
(3, 3, '2026-12-02', 'SÁNG', '08:00:00', '12:00:00', 20, 20, 'FULL', 'P.302 - Lầu 3', NOW(), NOW()),
(4, 4, '2026-12-02', 'CHIỀU', '13:30:00', '17:00:00', 15, 8, 'AVAILABLE', 'P.208 - Lầu 2', NOW(), NOW()),
(5, 5, '2026-12-03', 'SÁNG', '08:00:00', '12:00:00', 25, 12, 'AVAILABLE', 'P.102 - Lầu 1', NOW(), NOW()),
(6, 6, '2026-12-03', 'TỐI', '17:30:00', '20:30:00', 10, 2, 'AVAILABLE', 'P.205 - Lầu 2', NOW(), NOW()),
(7, 7, '2026-12-04', 'SÁNG', '08:00:00', '12:00:00', 15, 4, 'AVAILABLE', 'P.201 - Lầu 2', NOW(), NOW()),
(8, 8, '2026-12-04', 'CHIỀU', '13:30:00', '17:00:00', 15, 6, 'AVAILABLE', 'P.306 - Lầu 3', NOW(), NOW()),
(9, 9, '2026-12-05', 'SÁNG', '08:00:00', '12:00:00', 30, 18, 'AVAILABLE', 'P.108 - Lầu 1', NOW(), NOW()),
(10, 10, '2026-12-05', 'CHIỀU', '13:30:00', '17:00:00', 15, 0, 'AVAILABLE', 'P.301 - Lầu 3', NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `shift` = VALUES(`shift`),
    `room_number` = VALUES(`room_number`);
