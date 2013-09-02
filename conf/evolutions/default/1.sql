# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table record (
  id                        bigint not null,
  user_username             varchar(255),
  altitude                  double,
  latitude                  double,
  longtitude                double,
  speed                     double,
  accuracy                  double,
  note                      clob,
  time                      timestamp not null,
  constraint pk_record primary key (id))
;

create table user (
  username                  varchar(255) not null,
  password                  varchar(255),
  constraint pk_user primary key (username))
;

create sequence record_seq;

create sequence user_seq;

alter table record add constraint fk_record_user_1 foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_record_user_1 on record (user_username);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists record;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists record_seq;

drop sequence if exists user_seq;

