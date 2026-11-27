-- ========================================================
-- LAB 03: DỮ LIỆU MẪU CHO PATIENT SERVICE (MySQL - patient_db)
-- Đề tài: Quản lý phòng khám và quầy thuốc
-- Đảm bảo chuẩn hóa tiếng Việt có dấu đầy đủ (utf8mb4)
-- ========================================================

USE `patient_db`;
SET NAMES 'utf8mb4';

-- 1. Bảng patients (Danh sách bệnh nhân mẫu có dấu tiếng Việt)
INSERT INTO `patients` (`id`, `full_name`, `date_of_birth`, `gender`, `identity_card_number`, `phone_number`, `email`, `created_at`, `updated_at`) VALUES
(10, 'Nguyễn Văn An', '1990-03-12', 'Nam', '079190001234', '0901234567', 'an.nguyen@gmail.com', NOW(), NOW()),
(11, 'Trần Thị Bích', '1995-07-22', 'Nữ', '079195002345', '0912345678', 'bich.tran@gmail.com', NOW(), NOW()),
(12, 'Lê Hoàng Cường', '1988-11-05', 'Nam', '079188003456', '0923456789', 'cuong.le@gmail.com', NOW(), NOW()),
(13, 'Phạm Thị Dung', '2001-09-18', 'Nữ', '079201004567', '0934567890', 'dung.pham@gmail.com', NOW(), NOW()),
(14, 'Hoàng Văn Em', '1975-04-30', 'Nam', '079175005678', '0945678901', 'em.hoang@gmail.com', NOW(), NOW()),
(15, 'Đỗ Thị Hạnh', '1998-12-25', 'Nữ', '079198006789', '0956789012', 'hanh.do@gmail.com', NOW(), NOW()),
(16, 'Vương Quốc Hùng', '1982-08-14', 'Nam', '079182007890', '0967890123', 'hung.vuong@gmail.com', NOW(), NOW()),
(17, 'Bùi Thị Kim', '2003-02-09', 'Nữ', '079203008901', '0978901234', 'kim.bui@gmail.com', NOW(), NOW()),
(18, 'Đinh Công Long', '1992-06-17', 'Nam', '079192009012', '0989012345', 'long.dinh@gmail.com', NOW(), NOW()),
(19, 'Nguyễn Mai Linh', '1999-10-01', 'Nữ', '079199010123', '0990123456', 'linh.nguyen@gmail.com', NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `full_name` = VALUES(`full_name`),
    `gender` = VALUES(`gender`);

-- 2. Bảng patient_contacts (Liên hệ người thân có dấu tiếng Việt)
INSERT INTO `patient_contacts` (`patient_id`, `contact_name`, `relationship`, `phone_number`) VALUES
(10, 'Nguyễn Văn Bình', 'Bố', '0901111222'),
(11, 'Trần Văn Cảnh', 'Chồng', '0912222333'),
(12, 'Lê Thị Đào', 'Vợ', '0923333444'),
(13, 'Phạm Văn Giang', 'Anh trai', '0934444555'),
(14, 'Hoàng Thị Hoa', 'Vợ', '0945555666');

-- 3. Bảng patient_addresses (Địa chỉ bệnh nhân có dấu tiếng Việt)
INSERT INTO `patient_addresses` (`patient_id`, `address_line`, `ward`, `district`, `city`, `is_default`) VALUES
(10, '123 Nguyễn Trãi', 'Phường 2', 'Quận 5', 'TP. Hồ Chí Minh', 1),
(11, '456 Lê Duẩn', 'Phường Bến Nghé', 'Quận 1', 'TP. Hồ Chí Minh', 1),
(12, '789 Cách Mạng Tháng 8', 'Phường 7', 'Quận Tân Bình', 'TP. Hồ Chí Minh', 1),
(13, '12 Trần Phú', 'Phường 4', 'Quận 5', 'TP. Hồ Chí Minh', 1),
(14, '34 Hoàng Diệu', 'Phường 12', 'Quận 4', 'TP. Hồ Chí Minh', 1);

-- 4. Bảng patient_medical_histories (Tiền sử bệnh án có dấu tiếng Việt)
INSERT INTO `patient_medical_histories` (`patient_id`, `condition_name`, `diagnosed_date`, `notes`) VALUES
(10, 'Tăng huyết áp', '2021-05-10', 'Đang dùng thuốc hạ áp định kỳ'),
(11, 'Dị ứng phấn hoa', '2019-03-15', 'Tránh tiếp xúc mùa hoa nở'),
(12, 'Viêm dạ dày HP(+)', '2022-08-20', 'Đã điều trị xong liệu trình kháng sinh'),
(14, 'Đái tháo đường Type 2', '2020-01-11', 'Tiêm insulin và kiêng đường'),
(16, 'Hen phế quản', '2015-11-02', 'Có bình xịt Ventolin phòng cơn cấp');
