
insert into users(username, password, enabled) values ('user', '$2a$12$u4qBGD6cS2YcC2wj6HzGxev.ieU03TrG0My598eL72Vh1hVtxPA9e', true);
insert into users(username, password, enabled) values ('admin', '$2a$12$u4qBGD6cS2YcC2wj6HzGxev.ieU03TrG0My598eL72Vh1hVtxPA9e', true);
-- password = password
-- https://bcrypt-generator.com/
insert into authorities(username, authority) values ('user', 'USER');
insert into authorities(username, authority) values ('admin', 'USER');
insert into authorities(username, authority) values ('admin', 'ADMIN');