into userss (id, description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('4', 'soy bahui', 'FALSE', 'Juan', '12345678', '2013-08-01 00:00:26', 'jbuireo', 'quien soy?', 'Buireo', 'jbuireo', '0');
insert into userss (id, description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('5', 'soy faca', 'FALSE', 'Facundo', '12345678', '2013-08-01 00:00:27', 'fmenzella', 'quien soy?', 'Menzella', 'fmenzella', '0');
insert into userss (id, description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('6', 'soy masiii', 'FALSE', 'Maximiliano', '12345678', '2013-08-01 00:00:28', 'maxix', 'quien soy?', 'Valverde', 'maxix', '0');
insert into userss (id, description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('7', 'soy matix', 'FALSE', 'Matias', '12345678', '2013-08-01 00:00:29', 'matix', 'quien soy?', 'Rivas', 'matix', '0');
insert into userss (id, description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('8', 'soy esteporg', 'FALSE', 'Stefano', '12345678', '2013-08-01 00:00:30', 'esteporg', 'quien soy?', 'Baratuche', 'esteporg', '0');
insert into userss (id, description, isPrivate, name, password, registrationDate, secretAnswer, secretQuestion, surname, username, visits) values ('9', 'soy luchox', 'FALSE', 'Stefano', '12345678', '2013-08-01 00:00:31', 'luchox', 'quien soy?', 'Cappetta', 'luchox', '0');

insert into Notification (id, checked, date, notification, notificator_id) values ('1', 'FALSE', '2013-11-01 00:00:30', 'gerchux has mentioned you in a comment', '1');
insert into Comment (author_id, comment, date, originalAuthor_id) values ('1', 'Este comment menciona a @Gabox', '2013-11-01 00:00:30', '1');
insert into userss_Notification (userss_id, notifications_id) values ('3', '1');
insert into Comment_userss (Comment_id, references_id) values ('13', '3');

insert into Notification (id, checked, date, notification, notificator_id) values ('2', 'FALSE', '2013-11-01 00:00:30', 'gerchux is following you', '1');
insert into userss_Notification (userss_id, notifications_id) values ('2', '2');
insert into userss_userss (followers_id, following_id) values ('1', '2');

insert into Notification (id, checked, date, notification, notificator_id) values ('3', 'FALSE', '2013-11-01 00:00:30', 'gerchux is following you', '1');
insert into userss_Notification (userss_id, notifications_id) values ('3', '3');
insert into userss_userss (followers_id, following_id) values ('1', '3');

insert into Notification (id, checked, date, notification, notificator_id) values ('4', 'FALSE', '2013-11-01 00:00:30', 'gerchux is following you', '1');
insert into userss_Notification (userss_id, notifications_id) values ('8', '4');
insert into userss_userss (followers_id, following_id) values ('1', '8');

insert into Notification (id, checked, date, notification, notificator_id) values ('5', 'FALSE', '2013-11-01 00:00:30', 'matix is following you', '7');
insert into userss_Notification (userss_id, notifications_id) values ('2', '5');
insert into userss_userss (followers_id, following_id) values ('7', '2');

insert into Notification (id, checked, date, notification, notificator_id) values ('6', 'FALSE', '2013-11-01 00:00:30', 'matix is following you', '7');
insert into userss_Notification (userss_id, notifications_id) values ('3', '6');
insert into userss_userss (followers_id, following_id) values ('7', '3');

insert into Notification (id, checked, date, notification, notificator_id) values ('7', 'FALSE', '2013-11-01 00:00:30', 'matix is following you', '7');
insert into userss_Notification (userss_id, notifications_id) values ('8', '7');
insert into userss_userss (followers_id, following_id) values ('7', '8');

insert into Notification (id, checked, date, notification, notificator_id) values ('8', 'FALSE', '2013-11-01 00:00:30', 'jbuireo is following you', '4');
insert into userss_Notification (userss_id, notifications_id) values ('2', '8');
insert into userss_userss (followers_id, following_id) values ('4', '2');

insert into Notification (id, checked, date, notification, notificator_id) values ('9', 'FALSE', '2013-11-01 00:00:30', 'jbuireo is following you', '4');
insert into userss_Notification (userss_id, notifications_id) values ('3', '9');
insert into userss_userss (followers_id, following_id) values ('4', '3');

insert into Notification (id, checked, date, notification, notificator_id) values ('10', 'FALSE', '2013-11-01 00:00:30', 'fmenzella is following you', '5');
insert into userss_Notification (userss_id, notifications_id) values ('2', '10');
insert into userss_userss (followers_id, following_id) values ('5', '2');

insert into Notification (id, checked, date, notification, notificator_id) values ('11', 'FALSE', '2013-11-01 00:00:30', 'maxix is following you', '6');
insert into userss_Notification (userss_id, notifications_id) values ('2', '11');
insert into userss_userss (followers_id, following_id) values ('6', '2');

insert into Notification (id, checked, date, notification, notificator_id) values ('12', 'FALSE', '2013-11-01 00:00:30', 'maxix is following you', '6');
insert into userss_Notification (userss_id, notifications_id) values ('3', '12');
insert into userss_userss (followers_id, following_id) values ('6', '3');

insert into Notification (id, checked, date, notification, notificator_id) values ('13', 'FALSE', '2013-11-01 00:00:30', 'luchox is following you', '9');
insert into userss_Notification (userss_id, notifications_id) values ('2', '13');
insert into userss_userss (followers_id, following_id) values ('9', '2');

insert into Notification (id, checked, date, notification, notificator_id) values ('14', 'FALSE', '2013-11-01 00:00:30', 'luchox is following you', '9');
insert into userss_Notification (userss_id, notifications_id) values ('8', '14');
insert into userss_userss (followers_id, following_id) values ('9', '8');