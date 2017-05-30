
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `roles` (`id`, `role`)
VALUES
	(1,'ROLE_ADMIN'),
	(2,'ROLE_USER');


select * from roles
delete from roles

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`, `username`, `password`,`email`)
VALUES
	(1,'admin','admin','adminmail@mail'),
	(2,'leha','leha','leha@mail'),
	(3,'user','user','usermail@mail');

select * from users





DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `role_id` bigint(20) unsigned DEFAULT NULL,
  KEY `hasuser` (`user_id`),
  KEY `hasrole` (`role_id`),
  CONSTRAINT `hasrole` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasuser` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
	(1,1), (1,2), (2,1), (2,2), (3,2);


delete from  users_roles
select `user_id`, `role_id` from  users_roles

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `room_name` varchar(250) NOT NULL,
  KEY `id` (`id`),
  KEY `room_name` (`room_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `rooms` (`id`, `room_name`)
VALUES
	(1,'mainRoom'),
	(2,'customRoom'),
	(3,'guestRoom'),
	(4,'primeRoom'),
	(5,'moderRoom');

delete from rooms

select `room_name`, `id` from  `rooms`


DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `room_id` bigint(20) unsigned NOT NULL,
  `content` varchar(500) NOT NULL,
  `username` varchar(50) NOT NULL,
  `date` DATETIME NOT NULL,
  KEY `id` (`id`),
  PRIMARY KEY (`id`),
  CONSTRAINT `messagehasroom` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `messages` (`id`, `room_id`, `content`, `username`, `date`)
VALUES
	(1, 1, '1 message', 'user', '2017-05-23 15:40:00' ),
	(2, 1, '2 message', 'admin', '2017-05-23 15:41:00'),
	(3, 1, '3 message', 'user', '2017-05-23 15:42:00' ),
	(4, 1, '4 message', 'admin', '2017-05-23 15:43:00');

	select * from `messages`

	delete from `messages`



DROP TABLE IF EXISTS `users_rooms`;
CREATE TABLE `users_rooms` (
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `room_id` bigint(20) unsigned DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `userhasroom` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `roomhasuser` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users_rooms` (`user_id`, `room_id`)
VALUES
	(1,1), (1,2), (1,3), (1,4), (1,5),
	(2,1), (2,2), (2,3), (2,4), (2,5),
     (3,2), (3,3), (3,4);

delete from `users_rooms`

select `user_id`, `room_id` from `users_rooms`