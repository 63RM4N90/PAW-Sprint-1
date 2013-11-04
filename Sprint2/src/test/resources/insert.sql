insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy gerchux', 'FALSE', 'German', '12345678', '2013-08-01 00:00:23', 'gerchux', 'quien soy?', 'Romarion', 'gerchux', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy florchax', 'FALSE', 'Florencia', '12345678', '2013-08-01 00:00:24', 'florchax', 'quien soy?', 'Besteiro', 'florchax', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy gabox', 'FALSE', 'Gabriel', '12345678', '2013-08-01 00:00:25', 'gabox', 'quien soy?', 'Zanzotti', 'gabox', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy bahui', 'FALSE', 'Juan', '12345678', '2013-08-01 00:00:26', 'jbuireo', 'quien soy?', 'Buireo', 'jbuireo', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy faca', 'FALSE', 'Facundo', '12345678', '2013-08-01 00:00:27', 'fmenzella', 'quien soy?', 'Menzella', 'fmenzella', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy masiii', 'FALSE', 'Maximiliano', '12345678', '2013-08-01 00:00:28', 'maxix', 'quien soy?', 'Valverde', 'maxix', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy matix', 'FALSE', 'Matias', '12345678', '2013-08-01 00:00:29', 'matix', 'quien soy?', 'Rivas', 'matix', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy esteporg', 'FALSE', 'Stefano', '12345678', '2013-08-01 00:00:30', 'esteporg', 'quien soy?', 'Baratuche', 'esteporg', '0');
insert into userss (description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('soy luchox', 'FALSE', 'Stefano', '12345678', '2013-08-01 00:00:31', 'luchox', 'quien soy?', 'Cappetta', 'luchox', '0');

commit;

insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment no tiene hashtags', '2013-08-01 00:00:31', '1');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment tiene 2 hashtags: #a y #b', '2013-08-04 00:00:32', '1');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment tiene 1 hashtag: #prueba', '2013-08-25 00:00:33', '1');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('2', 'Este comment tiene 3 hashtags: #prueba, #c y #d', '2013-08-26 00:00:34', '2');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('2', 'Este comment tiene 1 hashtag: #d', '2013-08-30 00:00:35', '2');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('2', 'Este comment tiene 3 hashtags: #prueba, #e y #f', '2013-08-31 00:00:36', '2');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('3', 'Este comment tiene 2 hashtags: #d y #f', '2013-08-27 00:00:37', '3');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('3', 'Este comment tiene 3 hashtags: #prueba, #e y #g', '2013-09-17 00:00:38', '3');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment tiene 4 hashtag: #prueba, #d, #h e #i', '2013-09-18 00:00:40', '1');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment tiene 2 hashtag: #f #g', '2013-09-05 00:10:41', '1');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('2', 'Este comment tiene 3 hashtags: #prueba y #h', '2013-09-19 00:00:42', '2');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('2', 'Este comment tiene 5 hashtags: #f, #j, #k, #l y #m', '2013-09-05 00:00:43', '2');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('3', 'Este comment tiene 4 hashtags: #f, #d, #m y #g', '2013-09-19 00:00:44', '3');

insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment menciona a @florchax', '2013-08-01 00:00:45', '1');

commit;

insert into Hashtag (author_id, date, hashtag) values ('1', '2013-08-04 00:00:32', 'a');
insert into Hashtag (author_id, date, hashtag) values ('1', '2013-08-04 00:00:32', 'b');
insert into Hashtag (author_id, date, hashtag) values ('1', '2013-08-25 00:00:33', 'prueba');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-08-26 00:00:34', 'c');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-08-26 00:00:34', 'd');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-08-31 00:00:36', 'e');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-08-31 00:00:36', 'f');
insert into Hashtag (author_id, date, hashtag) values ('3', '2013-09-17 00:00:38', 'g');
insert into Hashtag (author_id, date, hashtag) values ('1', '2013-09-18 00:00:40', 'h');
insert into Hashtag (author_id, date, hashtag) values ('1', '2013-09-18 00:00:40', 'i');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-09-05 00:00:43', 'j');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-09-05 00:00:43', 'k');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-09-05 00:00:43', 'l');
insert into Hashtag (author_id, date, hashtag) values ('2', '2013-09-05 00:00:43', 'm');

commit;

insert into Comment_Hashtag (comments_id, hashtags_id) values ('2', '1');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('3', '3');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('4', '3');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('4', '4');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('4', '5');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('5', '5');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('6', '3');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('6', '6');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('6', '7');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('7', '5');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('7', '7');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('8', '3');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('8', '6');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('8', '8');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('9', '5');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('9', '7');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('10', '3');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('10', '5');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('10', '6');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('10', '10');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('11', '7');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('11', '8');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('12', '3');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('12', '9');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('13', '7');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('13', '11');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('13', '12');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('13', '13');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('13', '14');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('14', '7');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('14', '14');
insert into Comment_Hashtag (comments_id, hashtags_id) values ('14', '8');

commit;

insert into Notification (checked, date, notification, notificator_id) values ('FALSE', '2013-08-01 00:00:45', 'gerchux has mentioned you in a comment.', '1');

commit;

insert into userss_Notification (userss_id, notifications_id) values ('2', '1');

commit;

insert into Comment_userss (Comment_id, references_id) values ('15', '2');

commit;


delete from userss_Notification where userss_id=?
insert into userss_Notification (userss_id, notifications_id) values (?, ?)
insert into userss_Notification (userss_id, notifications_id) values (?, ?)
insert into userss_userss (followers_id, following_id) values (?, ?)