insert into USER (IS_ADMIN, LOGIN_ID, NAME, PASSWORD) values (true,'admin','Admin','A+98FnMIylJGjl2jhHcUYQ==');

insert into question (created_by, heading, text, tag, up_vote) values (1, 'is wicket nice ?', 'Wicket is nice', 'java,wicket', 1);
insert into answer (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ( (select isnull(max(id),0) from answer where question_id=1) + 1,1, null, 1, 1, 'No', 1);
insert into question_comment (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ((select isnull(max(id),0) from question_comment  where question_id=1) + 1, 1, null, 1, 1, 'are you asking?', 1);
insert into answer_comment (id,created_by, updated_at, updated_by,answer_id, question_id, text, up_vote) values ((select isnull(max(id),0) from answer_comment  where question_id=1 and answer_id=1) + 1, 1, null, 1,1, 1, 'are you asking or not asking?', 1);


insert into question (created_by, heading, text, tag, up_vote) values (1, 'is spring nice ?', 'Spring is nice & the best framework to work on as it is modular and easy to understand bla bla bla', 'java,spring,html', 3);
insert into answer (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ( (select isnull(max(id),0) from answer where question_id=2) + 1,1, null, 1, 2, 'No', 1);
insert into answer (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ( (select isnull(max(id),0) from answer where question_id=2) + 1,1, null, 1, 2, 'No', 1);
insert into question_comment (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ((select isnull(max(id),0) from question_comment  where question_id=2) + 1, 1, null, 1, 2, 'are you asking?', 1);
insert into answer_comment (id,created_by, updated_at, updated_by,answer_id, question_id, text, up_vote) values ((select isnull(max(id),0) from answer_comment  where question_id=2 and answer_id=1) + 1, 1, null, 1,1,2, 'are you asking or not asking?', 1);
insert into answer_comment (id,created_by, updated_at, updated_by,answer_id, question_id, text, up_vote) values ((select isnull(max(id),0) from answer_comment  where question_id=2 and answer_id=2) + 1, 1, null, 1,2,2, 'are you asking or not asking?', 1);


insert into question (created_by, heading, text, tag, up_vote) values ( 1, 'is hibernate nice ?', 'Hibernate is nice', 'java,hibernate,sql', 1);
insert into answer (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ( (select isnull(max(id),0) from answer where question_id=3) + 1,1, null, 1, 3, 'No', 1);
insert into question_comment (id,created_by, updated_at, updated_by, question_id, text, up_vote) values ((select isnull(max(id),0) from question_comment  where question_id=3) + 1, 1, null, 1, 3, 'are you asking?', 1);
insert into answer_comment (id,created_by, updated_at, updated_by,answer_id, question_id, text, up_vote) values ((select isnull(max(id),0) from answer_comment  where question_id=3 and answer_id=1) + 1, 1, null, 1,1, 3, 'are you asking or not asking?', 1);