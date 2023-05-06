CREATE DATABASE onboarding;

//for multiple addresses need to have another mapping table



CREATE TABLE `driver` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `address_id` int DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  CONSTRAINT `driver_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
)



CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `pincode` int DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) 



CREATE TABLE onboard_tracking (
	driver_id int,
	status ENUM('INITIATION_FAILURE','INITIATED', 'SUCCESS', 'FAILURE','PARTIAL_FAILURE'),
	updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	updated_by varchar(255) NOT NULL,
	FOREIGN KEY (driver_id) REFERENCES driver (id)
)

CREATE TABLE IF NOT EXISTS document(
	id int AUTO_INCREMENT,
	content MEDIUMBLOB  NOT NULL,
	created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	created_by varchar(255) NOT NULL,
	PRIMARY KEY (id)
)


CREATE TABLE document_validation_status (
	document_id int,
	is_valid BOOLEAN,
	details varchar(255) NOT NULL,
	updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	updated_by varchar(255) NOT NULL,
	FOREIGN KEY (document_id) REFERENCES document (id)
)


CREATE TABLE IF NOT EXISTS vehicle (
	id int AUTO_INCREMENT,
	model varchar(255) NOT NULL,
	type varchar(255) NOT NULL,
	created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	created_by varchar(255) NOT NULL,
	PRIMARY KEY (id)
)


CREATE TABLE IF NOT EXISTS driver_vehicle_mapping (
	id int AUTO_INCREMENT,
	driver_id int,
	vehicle_id int,
	created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	created_by varchar(255) NOT NULL,
	FOREIGN KEY (driver_id) REFERENCES driver (id),
	FOREIGN KEY (vehicle_id) REFERENCES vehicle (id),
	PRIMARY KEY (id)	
)

CREATE TABLE driver_vehicle_document_mapping (
id int AUTO_INCREMENT,
driver_id int,
vehicle_id int,
document_id int,
is_active BOOLEAN,
created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
created_by varchar(255) NOT NULL,
updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
updated_by varchar(255) NOT NULL,
FOREIGN KEY (driver_id) REFERENCES driver (id),
FOREIGN KEY (vehicle_id) REFERENCES vehicle (id),
FOREIGN KEY (document_id) REFERENCES document (id),
PRIMARY KEY (id)
	)

