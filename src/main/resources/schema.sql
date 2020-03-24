drop table user if exists;

drop table question_comment if exists;

drop table answer_comment if exists;

drop table answer if exists;

drop table question if exists;

create table user(
id identity constraint pk_user primary key,
is_admin boolean default false,
is_locked boolean default false,
login_id varchar(100) unique not null,
password varchar(255) not null,
name varchar(255) not null
);

create table question(
id identity constraint pk_question primary key,
heading varchar(50) not null,
text clob not null,
tag varchar(255),
up_vote long default 0,
created_at timestamp default CURRENT_TIMESTAMP,
created_by long not null,
updated_at timestamp default null,
updated_by long default null
);

create table answer(
id long not null,
question_id long not null,
text clob not null,
up_vote long default 0,
created_at timestamp default CURRENT_TIMESTAMP,
created_by long not null,
updated_at timestamp default null,
updated_by long default null,
constraint fk_answer foreign key(question_id) references question(id),
constraint pk_answer primary key(id,question_id)
);

create table question_comment(
id long not null,
question_id long not null,
text clob not null,
up_vote long default 0,
created_at timestamp default CURRENT_TIMESTAMP,
created_by long not null,
updated_at timestamp default null,
updated_by long default null,
constraint fk_question_comment foreign key(question_id) references question(id),
constraint pk_question_comment primary key(id,question_id)
);

create table answer_comment(
id long not null,
answer_id long not null,
question_id long not null,
text clob not null,
up_vote long default 0,
created_at timestamp default CURRENT_TIMESTAMP,
created_by long not null,
updated_at timestamp,
updated_by long,
constraint fk_answer_comment  foreign key(answer_id,question_id) references answer(id,question_id),
constraint pk_answer_comment primary key(id,answer_id,question_id)
);