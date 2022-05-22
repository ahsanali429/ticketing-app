CREATE TABLE IF NOT EXISTS user_info(
                      id integer unsigned not null auto_increment,
                      user_name varchar(50) not null unique,
                      password varchar(100) not null,
                      created_at timestamp default current_timestamp(),
                      updated_at timestamp default current_timestamp() on update current_timestamp(),
                      primary key (id)
);

CREATE TABLE IF NOT EXISTS restaurant(
                    restaurant_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
                    mean_time_to_prepare_food_in_seconds INTEGER NOT NULL,
                    created_at timestamp default current_timestamp(),
                    updated_at timestamp default current_timestamp() on update current_timestamp(),
                    primary key (restaurant_id)
);

CREATE TABLE IF NOT EXISTS delivery(
                     delivery_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
                     restaurant_id INTEGER UNSIGNED NOT NULL,
                     customer_type enum('VIP','LOYAL','NEW') NOT NULL,
                     delivery_status enum('RECEIVED', 'PREPARING', 'PICKED_UP', 'DELIVERED') NOT NULL,
                     current_distance_from_destination_in_metres integer NOT NULL,
                     expected_delivery_time TIMESTAMP NOT NULL,
                     time_to_reach_destination_in_seconds INTEGER NOT NULL,
                     created_at timestamp default current_timestamp(),
                     updated_at timestamp default current_timestamp() on update current_timestamp(),

                     primary key (delivery_id),
                     foreign key (restaurant_id) references restaurant(restaurant_id)
);

CREATE TABLE IF NOT EXISTS ticket(
                       ticket_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
                       reason_type varchar(100) NOT NULL,
                       delivery_id INTEGER UNSIGNED NOT NULL,
                       ticket_priority enum('HIGH', 'MEDIUM', 'LOW') NOT NULL,
                       created_at timestamp default current_timestamp(),
                       updated_at timestamp default current_timestamp() on update current_timestamp(),

                       primary key (ticket_id),
                       foreign key (delivery_id) references delivery(delivery_id),
                       unique key unique_delivery_reason (delivery_id, reason_type)

);

INSERT INTO user_info (user_name, password) VALUES ('ahsan', 'avbBYA8y4GWNnEmzTQmmea4ubZRFGHGJ');
