ALTER TABLE my_image DROP CONSTRAINT FK_my_image_UPLOADER_ID;
ALTER TABLE my_user_my_user DROP CONSTRAINT FK_my_user_my_user_User_ID;
ALTER TABLE my_user_my_user DROP CONSTRAINT FK_my_user_my_user_contacts_ID;
DROP TABLE my_user CASCADE;
DROP TABLE my_image CASCADE;
DROP TABLE my_user_my_user CASCADE;
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';
