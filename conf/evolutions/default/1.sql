# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "MEMBERS" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"NAME" VARCHAR NOT NULL,"MAIL" VARCHAR NOT NULL,"MESSAGE" VARCHAR NOT NULL);
create table "MESSAGES" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"MEMBER_ID" BIGINT NOT NULL,"MESSAGE" VARCHAR NOT NULL,"POSTDATE" TIMESTAMP NOT NULL);
alter table "MESSAGES" add constraint "MEMBER_FK" foreign key("MEMBER_ID") references "MEMBERS"("ID") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "MESSAGES" drop constraint "MEMBER_FK";
drop table "MESSAGES";
drop table "MEMBERS";

