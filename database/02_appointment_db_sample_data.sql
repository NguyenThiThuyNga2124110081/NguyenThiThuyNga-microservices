-- ========================================================
-- LAB 03: DỮ LIỆU MẪU CHO APPOINTMENT SERVICE (SQL Server - appointment_db)
-- Đề tài: Quản lý phòng khám và quầy thuốc
-- Đảm bảo chuẩn hóa tiếng Việt có dấu đầy đủ (NVARCHAR & Tiền tố N)
-- ========================================================

USE [appointment_db];
GO

-- 1. Bảng appointments (Lịch hẹn khám bệnh)
INSERT INTO [appointments] ([patient_id], [doctor_id], [doctor_schedule_id], [doctor_name], [department], [appointment_date_time], [status], [reason], [created_at], [updated_at]) VALUES
(10, 1, 1, N'BS.CKII Trần Văn Minh', N'Khoa Tim Mạch', '2026-12-01 08:30:00', 'CONFIRMED', N'Khám định kỳ huyết áp và tim mạch', GETDATE(), GETDATE()),
(11, 2, 2, N'ThS.BS Lê Thị Lan', N'Khoa Da Liễu', '2026-12-01 09:15:00', 'PENDING', N'Dị ứng ngứa da tay', GETDATE(), GETDATE()),
(12, 3, 3, N'BS.CKI Phạm Quốc Huy', N'Khoa Tiêu Hóa', '2026-12-02 10:00:00', 'CONFIRMED', N'Đau bụng thượng vị âm ỉ', GETDATE(), GETDATE()),
(13, 4, 4, N'BS.CKII Hoàng Ngọc Mai', N'Khoa Sản Phụ', '2026-12-02 14:00:00', 'PENDING', N'Khám thai định kỳ tuần thứ 24', GETDATE(), GETDATE()),
(14, 7, 7, N'PGS.TS Lê Quốc Cường', N'Khoa Nội Tiết', '2026-12-03 08:00:00', 'CONFIRMED', N'Theo dõi chỉ số đường huyết', GETDATE(), GETDATE()),
(15, 5, 5, N'ThS.BS Nguyễn Văn Đạt', N'Khoa Tai Mũi Họng', '2026-12-03 10:30:00', 'CANCELLED', N'Đau họng hắt hơi sổ mũi', GETDATE(), GETDATE()),
(16, 6, 6, N'BS.CKI Vũ Thị Thảo', N'Khoa Hô Hấp', '2026-12-04 09:00:00', 'CONFIRMED', N'Khó thở và ho về đêm', GETDATE(), GETDATE()),
(17, 3, 3, N'BS.CKI Phạm Quốc Huy', N'Khoa Tiêu Hóa', '2026-12-04 15:30:00', 'PENDING', N'Rối loạn tiêu hóa kéo dài', GETDATE(), GETDATE()),
(18, 2, 2, N'ThS.BS Lê Thị Lan', N'Khoa Da Liễu', '2026-12-05 08:45:00', 'CONFIRMED', N'Khám mụn trứng cá nặng', GETDATE(), GETDATE()),
(19, 5, 5, N'ThS.BS Nguyễn Văn Đạt', N'Khoa Tai Mũi Họng', '2026-12-05 11:00:00', 'COMPLETED', N'Kiểm tra thính lực và nội soi tai', GETDATE(), GETDATE());
GO

-- 2. Bảng appointment_details (Chi tiết lịch hẹn)
INSERT INTO [appointment_details] ([appointment_id], [consultation_type], [estimated_duration_minutes], [symptoms], [fee]) VALUES
(2, N'Khám chuyên khoa', 30, N'Tức ngực, khó thở nhẹ khi gắng sức', 300000.0),
(2, N'Khám trực tiếp', 20, N'Nổi mẩn đỏ và ngứa vùng cánh tay', 250000.0);
GO

-- 3. Bảng appointment_notes (Ghi chú lịch hẹn)
INSERT INTO [appointment_notes] ([appointment_id], [created_by], [note], [created_at]) VALUES
(2, N'Tiếp tân Nga', N'Bệnh nhân đã xác nhận cuộc gọi lúc 08:00', GETDATE()),
(2, N'Bác sĩ Cường', N'Nhắc bệnh nhân tái khám sau 2 tuần', GETDATE());
GO

-- 4. Bảng appointment_status_histories (Lịch sử trạng thái lịch hẹn)
INSERT INTO [appointment_status_histories] ([appointment_id], [previous_status], [new_status], [changed_by], [change_reason], [changed_at]) VALUES
(2, 'PENDING', 'CONFIRMED', N'Tiếp tân Nga', N'Gọi điện xác nhận thành công với bệnh nhân', GETDATE()),
(2, 'CONFIRMED', 'COMPLETED', N'Bác sĩ Cường', N'Đã hoàn thành khám và kê toa thuốc', GETDATE());
GO
