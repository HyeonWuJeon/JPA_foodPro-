    create table if not exists member (
       member_id  bigint not null auto_increment,
        city varchar(255) not null,
        street varchar(255) not null,
        zipcode varchar(255) not null,
        birth varchar(255) not null,
        email varchar(255) not null UNIQUE,
        low_pwd varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        pwd varchar(255) not null,
        role varchar(255) not null,
        primary key (member_id)
    ) engine=InnoDB;

    create table if not exists item (
       ftype varchar(31) not null,
        item_id bigint not null auto_increment,
        name varchar(255),
        price integer not null,
        stock_quantity integer not null,
        primary key (item_id)
    ) engine=InnoDB;

    create table if not exists book (
       author varchar(255),
        item_id bigint not null,
        primary key(item_id)
--        foreign key (item_id)
--        references item(item_id)
--        ON DELETE CASCADE
--        ON UPDATE CASCADE
    ) engine=InnoDB;

       create table if not exists clothes (
       sizes varchar(255),
        item_id bigint not null,
        primary key (item_id)
--        foreign key(item_id)
--        reference item(item_id)
    ) engine=InnoDB;


    create table if not exists food (
       expiration_date varchar(255),
        item_id bigint not null,
        primary key (item_id)
--        foreign key(item_id)
--        reference item(item_id)
    ) engine=InnoDB;


     create table catagory (
       catagory_id bigint not null auto_increment,
        name varchar(255),
        parent_id bigint,
        primary key (catagory_id)
    ) engine=InnoDB

    create table catagory_item (
       order_food_id bigint not null auto_increment,
        create_date datetime(6),
        modified_date datetime(6),
        catagory_id bigint,
        item_id bigint,
        primary key (order_food_id)
    ) engine=InnoDB;


        create table if not exists delivery (
       delivery_id bigint not null auto_increment,
        city varchar(255),
        street varchar(255),
        zipcode varchar(255),
        delivery_status varchar(255),
        primary key (delivery_id)
    ) engine=InnoDB;



     create table order_item (
       order_food_id bigint not null auto_increment,
        create_date datetime(6),
        modified_date datetime(6),
        count integer not null,
        order_price integer not null,
        food_id bigint,
        order_id bigint,
        primary key (order_food_id)
    ) engine=InnoDB;

      create table orders (
       order_id bigint not null auto_increment,
        create_date datetime(6),
        modified_date datetime(6),
        order_date datetime(6),
        order_status varchar(255),
        delivery_id bigint,
        member_id bigint,
        primary key (order_id)
    ) engine=InnoDB;


alter table book
       add constraint FKgohd8evkxf3j9a0p4jk5evpiv
       foreign key (item_id)
       references item (item_id)

alter table clothes
       add constraint FKfrrypr5bounfnme0otnw6ntnj
       foreign key (item_id)
       references item (item_id)


alter table food
       add constraint FKgw9gw7sxmirs8u5hdgqu5pvdy
       foreign key (item_id)
       references item (item_id)

alter table catagory
       add constraint FKpskttdxiu2742f8j4lgdvkgaa
       foreign key (parent_id)
       references catagory (catagory_id)

alter table catagory_item
       add constraint FKiu4kgumc6q4nr6b06a4tluvqc
       foreign key (catagory_id)
       references catagory (catagory_id)

alter table order_item
       add constraint FK5lxslc3b71oqmu32lt4jj9ego
       foreign key (item_id)
       references item (item_id)

alter table order_item
       add constraint FKt4dc2r9nbvbujrljv3e23iibt
       foreign key (order_id)
       references orders (order_id)
       on UPDATE CASCADE

alter table orders
       add constraint FKtkrur7wg4d8ax0pwgo0vmy20c
       foreign key (delivery_id)
       references delivery (delivery_id)
       on UPDATE CASCADE


alter table orders
       add constraint FKpktxwhj3x9m4gth5ff6bkqgeb
       foreign key (member_id)
       references member (member_id)