insert into public.board_user(id, first_name, last_name, email, password, role, status) values (-1, 'Alex', 'Petrov', 'petrov@gmail.com', '$2a$12$/YM3Gigu6wstrG3FAeEkUeE44xIfgc17glySC7M6XBJycJrS/UEXq', 'USER', 'ACTIVE');
insert into public.board_user(id, first_name, last_name, email, password, role, status) values (-2, 'Danil', 'Berezov', 'berezov@gmail.com', '$2a$12$/YM3Gigu6wstrG3FAeEkUeE44xIfgc17glySC7M6XBJycJrS/UEXq', 'USER', 'ACTIVE');
insert into public.board_user(id, first_name, last_name, email, password, role, status) values (-3, 'Sergey', 'Vishnevskiy', 'vishnevskiy@gmail.com', '$2a$12$/YM3Gigu6wstrG3FAeEkUeE44xIfgc17glySC7M6XBJycJrS/UEXq', 'USER', 'ACTIVE');
insert into public.board_user(id, first_name, last_name, email, password, role, status) values (-4, 'Irina', 'Miheeva', 'miheeva@gmail.com', '$2a$12$/YM3Gigu6wstrG3FAeEkUeE44xIfgc17glySC7M6XBJycJrS/UEXq', 'USER', 'ACTIVE');
insert into public.board_user(id, first_name, last_name, email, password, role, status) values (-5, 'Tatiana', 'Yakovleva', 'yakovleva@gmail.com', '$2a$12$/YM3Gigu6wstrG3FAeEkUeE44xIfgc17glySC7M6XBJycJrS/UEXq', 'USER', 'ACTIVE');
insert into public.board_user(id, first_name, last_name, email, password, role, status) values (-6, 'Pavel', 'Tkachev', 'tkachev@gmail.com', '$2a$12$Tj5Y/EA/CPiGBYkJNi1fRedJQrjAl3QJYMLup2KHpHpXjL9WRoGAW', 'ADMIN', 'ACTIVE');


insert into public.category(id, title) values (-1, 'books');
insert into public.category(id, title) values (-2, 'phones');


insert into public.chat(id) values (-1);
insert into public.chat(id) values (-2);
insert into public.chat(id) values (-3);


insert into public.user_chat(user_id, chat_id) values (-1, -1);
insert into public.user_chat(user_id, chat_id) values (-2, -1);
insert into public.user_chat(user_id, chat_id) values (-2, -2);
insert into public.user_chat(user_id, chat_id) values (-3, -2);
insert into public.user_chat(user_id, chat_id) values (-3, -3);
insert into public.user_chat(user_id, chat_id) values (-4, -3);


insert into public.message(id, content, date, status, chat_id, sender_id) values (-1, 'How much does it cost?', '01.10.2022 09:20:00', 'SENT', -1, -1);
insert into public.message(id, content, date, status, chat_id, sender_id) values (-2, '1000!', '01.10.2022 09:21:00', 'SENT', -1, -2);
insert into public.message(id, content, date, status, chat_id, sender_id) values (-3, 'How much?', '02.10.2022 10:22:00', 'SENT', -2, -2);
insert into public.message(id, content, date, status, chat_id, sender_id) values (-4, '99999$', '02.10.2022 10:25:00', 'SENT', -2, -3);
insert into public.message(id, content, date, status, chat_id, sender_id) values (-5, 'How are you??', '04.10.2022 15:40:00', 'SENT', -3, -3);
insert into public.message(id, content, date, status, chat_id, sender_id) values (-6, 'I am great!', '04.10.2022 15:42:00', 'SENT', -3, -4);


insert into public.ad(id, title, description, price, status, creation_date, promotion, promotion_end_date, owner_id, category_id) values (-1, 'Crime and Punishment', 'Sell book at cost', 500, 'ACTIVE', '01.09.2022', true, null, -1, -1);
insert into public.ad(id, title, description, price, status, creation_date, promotion, promotion_end_date, owner_id, category_id) values (-2, 'Java 8 Herbert Schildt', 'Java 8 book latest edition', 3000, 'ACTIVE', '05.09.2021', true, null, -2, -1);
insert into public.ad(id, title, description, price, status, creation_date, promotion, promotion_end_date, owner_id, category_id) values (-3, 'Martin Eden Jack Lodnon', 'I will give you this book very cheaply. Write in messages', 100, 'ACTIVE', '09.10.2022', false, null, -3, -1);
insert into public.ad(id, title, description, price, status, creation_date, promotion, promotion_end_date, owner_id, category_id) values (-4, 'IPhone 6 SE(restored)', 'I am selling it with the charging cable!', 7000, 'ACTIVE', '05.07.2022', false, null, -3, -2);
insert into public.ad(id, title, description, price, status, creation_date, promotion, promotion_end_date, owner_id, category_id) values (-5, 'Xiaomi Redmi Note 8 PRO', 'The screen is scratched', 5000, 'ACTIVE', '20.05.2022', false, null, -4, -2);
insert into public.ad(id, title, description, price, status, creation_date, promotion, promotion_end_date, owner_id, category_id) values (-6, 'Nokia 3310', 'The best phone in the world', 100000, 'ACTIVE', '01.01.2022', false, null, -5, -2);


insert into public.review(id, score, comment, author_id, ad_id) values (-1, 5, 'everything went perfectly', -2, -1);
insert into public.review(id, score, comment, author_id, ad_id) values (-2, 5, 'everything went perfectly', -3, -1);
insert into public.review(id, score, comment, author_id, ad_id) values (-3, 4, 'everything is fine', -4, -1);
insert into public.review(id, score, comment, author_id, ad_id) values (-4, 5, 'everything went perfectly', -1, -2);
insert into public.review(id, score, comment, author_id, ad_id) values (-5, 2, 'goods in bad condition', -3, -2);
insert into public.review(id, score, comment, author_id, ad_id) values (-6, 3, 'goods in bad condition', -4, -2);
insert into public.review(id, score, comment, author_id, ad_id) values (-7, 5, 'everything went perfectly', -1, -3);
insert into public.review(id, score, comment, author_id, ad_id) values (-8, 1, 'goods in bad condition', -2, -3);
insert into public.review(id, score, comment, author_id, ad_id) values (-9, 4, 'everything is fine', -4, -3);
insert into public.review(id, score, comment, author_id, ad_id) values (-10, 5, 'everything went perfectly', -1, -4);
insert into public.review(id, score, comment, author_id, ad_id) values (-11, 5, 'everything went perfectly', -2, -4);
insert into public.review(id, score, comment, author_id, ad_id) values (-12, 2, 'goods in bad condition', -5, -4);
insert into public.review(id, score, comment, author_id, ad_id) values (-13, 1, 'goods in bad condition', -1, -5);
insert into public.review(id, score, comment, author_id, ad_id) values (-14, 5, 'everything went perfectly', -2, -5);
insert into public.review(id, score, comment, author_id, ad_id) values (-15, 3, 'goods in bad condition', -5, -5);
insert into public.review(id, score, comment, author_id, ad_id) values (-16, 5, 'everything went perfectly', -1, -6);
insert into public.review(id, score, comment, author_id, ad_id) values (-17, 5, 'everything went perfectly', -2, -6);
insert into public.review(id, score, comment, author_id, ad_id) values (-18, 5, 'everything went perfectly', -3, -6);






