-- Test values for USER LISTS
insert into userss_w (description, isprivate, name, password, registrationdate, secretanswer, secretquestion, surname, username, visits) values ('im a new user :P', 'TRUE', 'test', 'password', '2014-12-08 22:34:52', 'potato', 'favourite vegetable?', 'user', 'test_user', '0');
insert into userlist (_name, _owner_id) values ('user_list_1', (select id from userss_w where id = (select max(id) from userss_w)));
insert into userss_w_userlist (_users_id, lists_id) values ((select id from userss_w where id = (select min(id) from userss_w)), (select id from userlist where id = (select max(id) from userlist)));

-- Test values for BLACKLISTS
insert into userss_blacklists (blacklisted_by_id, blacklisted_users_id) values ((select id from userss_w where id = (select max(id) from userss_w)), (select id from userss_w where id = (select min(id) from userss_w)));

-- Test values for PUBLICITIES
insert into publicity (_client_name, _image_url, _redirection_url) values ('test_client_1', 'http://www.dreamfly.co.uk/images/last-five-years-theatre-publicity-design-400.jpg', 'http://www.dreamfly.co.uk/');
insert into publicity (_client_name, _image_url, _redirection_url) values ('test_client_2', 'http://4.bp.blogspot.com/_J46IM9u3bEs/TJmE8rhKnNI/AAAAAAAAAGo/eaIvgDh0i1k/s1600/afiche+fx+-1.ai.jpg', 'http://publicitytadeo.blogspot.com.ar/2010_09_01_archive.html');
insert into publicity (_client_name, _image_url, _redirection_url) values ('test_client_3', 'http://www2.psd100.com/wp-content/uploads/2013/01/supermarket-flyer-template-psd-file.jpg', 'http://www.psd100.com/supermarket-flyer-template-psd-file5/#.VIZTQqSG_B8');
