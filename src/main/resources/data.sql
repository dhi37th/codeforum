insert into USER (IS_ADMIN, LOGIN_ID, NAME, PASSWORD) values (true,'admin','Admin','A+98FnMIylJGjl2jhHcUYQ==');
insert into USER (IS_ADMIN, LOGIN_ID, NAME, PASSWORD) values (false,'user','User','A+98FnMIylJGjl2jhHcUYQ==');

insert into question (id, created_at, created_by, updated_at, updated_by, heading, issue, tag, up_vote) values (null, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 'is wicket nice ?', 'Wicket is nice', 'java,wicket', 1);
insert into answer (id, created_at, created_by, updated_at, updated_by, question_id, text, up_vote) values (null, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1, 'oh it is', 1);
insert into comment (id, created_at, created_by, updated_at, updated_by, answer_id, question_id, text, up_vote) values (null, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, null, 1, 'is it?', 1);
