-- Test values for USER LISTS
insert into userss (description, isprivate, name, password, registrationdate, secretanswer, secretquestion, surname, username, visits) values ('im a new user :P', 'TRUE', 'test_1', 'password', '2014-12-08 22:34:52', 'potato', 'favourite vegetable?', 'user_1', 'test_user_1', '0');
insert into comment (author_id, comment, date, originalAuthor_id) values ((select id from userss where username='test_user_1'), 'Este comment es del usuario test_1', '2014-12-01 00:00:30', (select id from userss where username='test_user_1'));
insert into userss (description, isprivate, name, password, registrationdate, secretanswer, secretquestion, surname, username, visits) values ('im a new user too :P', 'FALSE', 'test_2', 'password', '2014-12-08 22:34:52', 'potato', 'favourite vegetable?', 'user_2', 'test_user_2', '0');
insert into userss (description, isprivate, name, password, registrationdate, secretanswer, secretquestion, surname, username, visits) values ('im a new user too :P', 'FALSE', 'test_3', 'password', '2014-12-08 22:34:52', 'potato', 'favourite vegetable?', 'user_3', 'test_user_3', '0');
insert into comment (author_id, comment, date, originalAuthor_id) values ((select id from userss where username='test_user_3'), 'Este comment es del usuario test_3', '2014-12-01 00:00:30', (select id from userss where username='test_user_3'));
insert into userlist (_name, _owner_id) values ('user_list_1', (select id from userss where username='test_user_2'));
insert into userss_userlist (_users_id, lists_id) values ((select id from userss where username='test_user_1'), (select id from userlist where _name='user_list_1'));
insert into userss_userlist (_users_id, lists_id) values ((select id from userss where username='test_user_3'), (select id from userlist where _name='user_list_1'));

-- Test values for BLACKLISTS
insert into userss_blacklists (blacklisted_by_id, blacklisted_users_id) values ((select id from userss where username='test_user_2'), (select id from userss where username='test_user_1'));

-- Test values for PUBLICITIES
insert into publicity (_client_name, _image_url, _redirection_url) values ('test_client_1', 'http://www.dreamfly.co.uk/images/last-five-years-theatre-publicity-design-400.jpg', 'http://www.dreamfly.co.uk/');
insert into publicity (_client_name, _image_url, _redirection_url) values ('test_client_2', 'http://4.bp.blogspot.com/_J46IM9u3bEs/TJmE8rhKnNI/AAAAAAAAAGo/eaIvgDh0i1k/s1600/afiche+fx+-1.ai.jpg', 'http://publicitytadeo.blogspot.com.ar/2010_09_01_archive.html');
insert into publicity (_client_name, _image_url, _redirection_url) values ('test_client_3', 'http://www2.psd100.com/wp-content/uploads/2013/01/supermarket-flyer-template-psd-file.jpg', 'http://www.psd100.com/supermarket-flyer-template-psd-file5/#.VIZTQqSG_B8');
