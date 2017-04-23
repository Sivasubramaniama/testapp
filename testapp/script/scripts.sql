
drop table `person`;

CREATE TABLE `person` (
                `id` int NOT NULL AUTO_INCREMENT,
                `name` varchar(100) NOT NULL,
                `email` varchar(100) NOT NULL,
                `country` varchar(30) NOT NULL,
                PRIMARY KEY (`id`)
);

CREATE TABLE `item` (
                `item_id` int NOT NULL AUTO_INCREMENT,
                `item_name` varchar(30) NOT NULL,
                `created_date` DATE NOT NULL,
                `is_active` TINYINT NOT NULL,
                PRIMARY KEY (`item_id`)
);

CREATE TABLE `product` (
                `p_id` int NOT NULL AUTO_INCREMENT,
                `product_name` varchar(30) NOT NULL,
                `created_date` DATE NOT NULL,
                `is_active` TINYINT NOT NULL,
                PRIMARY KEY (`p_id`)
);


CREATE TABLE `parent` (
                `pa_id` int NOT NULL AUTO_INCREMENT,
                `parent_name` varchar(30) NOT NULL,
                `boss` varchar(50),
                `created_date` DATE NOT NULL,
                `is_active` TINYINT NOT NULL,
                PRIMARY KEY (`pa_id`)
);

CREATE TABLE `category` (
                `c_id` int NOT NULL AUTO_INCREMENT,
                `category_name` varchar(30) NOT NULL,
                `created_date` DATE NOT NULL,
                PRIMARY KEY (`c_id`)
);

CREATE TABLE `address` (
                `a_id` int NOT NULL AUTO_INCREMENT,
                `street_name` varchar(50),
                `district` varchar(25) NOT NULL,
                `city` varchar(25) NOT NULL,
                `state` varchar(25) NOT NULL,
                `pincode` varchar(15) NOT NULL,
                `country` varchar(25) NOT NULL,
                `created_date` DATE NOT NULL,
                `is_active` TINYINT NOT NULL,
                PRIMARY KEY (`a_id`)
);

CREATE TABLE `barcode` (
                `b_id` int NOT NULL AUTO_INCREMENT,
                `type_name` varchar(25) NOT NULL,
                `identifier` varchar(50) NOT NULL,
                `is_active` TINYINT NOT NULL,
                PRIMARY KEY (`b_id`)
);

ALTER TABLE `item` ADD `product_id` int NOT NULL;
ALTER TABLE `item` ADD CONSTRAINT `item_product_fk0` FOREIGN KEY (`product_id`) REFERENCES `product`(`p_id`);

ALTER TABLE `item` ADD `barcode_id` int NOT NULL;
ALTER TABLE `item` ADD CONSTRAINT `item_barcode_fk0` FOREIGN KEY (`barcode_id`) REFERENCES `barcode`(`b_id`);

ALTER TABLE `product` ADD `parent_id` int NOT NULL;
ALTER TABLE `product` ADD CONSTRAINT `product_parent_fk0` FOREIGN KEY (`parent_id`) REFERENCES `parent`(`pa_id`);
ALTER TABLE `product` DROP `id`;

ALTER TABLE `product` ADD `category_id` int NOT NULL;
ALTER TABLE `product` ADD CONSTRAINT `product_category_fk0` FOREIGN KEY (`category_id`) REFERENCES `category`(`c_id`);

ALTER TABLE `parent` ADD `address_id` int NOT NULL;
ALTER TABLE `parent` ADD CONSTRAINT `parent_address_fk0` FOREIGN KEY (`address_id`) REFERENCES `address`(`a_id`);

update item set product_id='3' where item_id='2';


select * from item i inner join product p   on i.product_id =p.p_id inner join category c on c.c_id=p.category_id;
select * from product where p_id='2';
select * from category;

select p.p_id ,p.product_name,c.category_name from product p inner join category c on p.category_id=c.c_id 
inner join (select p.p_id ,c.category_name, c.c_id  from product p 
inner join category c on p.category_id=c.c_id where Product_name ='PepsiCo') a on a.c_id =c.c_id and a.p_id <> p.p_id;

select p,c from Product p inner join Category c on p.category_id=c.c_id 
inner join (select p.p_id ,c.category_name, c.c_id  from Product p 
inner join Category c on p.category_id=c.c_id where Product_name ='PepsiCo') a on a.c_id =c.c_id and a.p_id <> p.p_id;

select p.p_id ,p.product_name,c.category_name from Product p inner join Category c on p.category_id=c.c_id 
inner join parent Pa on pa.pa_id=p.parent_id 
inner join address ad on ad.a_id=pa.address_id
inner join (select p.p_id ,c.category_name, c.c_id  from Product p 
inner join Category c on p.category_id=c.c_id inner join parent Pa on pa.pa_id=p.parent_id 
inner join address ad on ad.a_id=pa.address_id where Product_name ='PepsiCo') a on a.c_id =c.c_id and a.p_id <> p.p_id
where ad.country='India';



select * from product p inner join category c on p.category_id=c.c_id where c.category_name='soap'







