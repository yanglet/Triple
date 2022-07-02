use triple;

create table point (
       point_id bigint not null auto_increment,
        create_time datetime(6),
        modified_time datetime(6),
        total_point integer not null,
        user_id varchar(255),
        primary key (point_id)
) engine=InnoDB;

alter table point add constraint idx_point unique (user_id);

create table point_log (
       point_log_id bigint not null auto_increment,
        create_time datetime(6),
        modified_time datetime(6),
        action varchar(255),
        point integer not null,
        review_id varchar(255),
        user_id varchar(255),
        primary key (point_log_id)
) engine=InnoDB;

create index ui_idx_point_log on point_log (user_id);
create index ri_idx_point_log on point_log (review_id);

create table review (
       review_id varchar(255) not null,
        content TEXT,
        is_first bit not null,
        place_id varchar(255),
        user_id varchar(255),
        primary key (review_id)
) engine=InnoDB;

create table review_attached_photo_ids (
       review_id varchar(255) not null,
        attached_photo_ids varchar(255)
) engine=InnoDB;

alter table review_attached_photo_ids add constraint FK2ritt7kb82s3h8v80t946n7hs foreign key (review_id) references review (review_id);