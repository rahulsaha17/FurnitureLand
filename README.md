# FurnitureLand - Inventory & Billing Management System  

FurnitureLand is a **Java Spring Boot** application designed to manage furniture store inventory and billing. It enables store owners to manage customers, products, billing, and discounts via coupon codes.  

## Features  
- **Customer Management**: Add, retrieve, and update customer details.  
- **Product Management**: Add, search, and retrieve products.  
- **Billing System**: Generate bills, apply percentage-based discounts, and manage transactions.  
- **Coupon System**: Assign coupons with percentage discounts to specific customers.  
- **Billing Cancellation**: Cancel a bill if required.  

##  Tech Stack  
- **Backend**: Java, Spring Boot  
- **Database**: PostgreSQL  
- **ORM**: Hibernate/JPA  
- **Build Tool**: Maven  

## Installation & Setup  
1. Clone the repository:  
   ```sh
   git clone https://github.com/rahulsaha17/FurnitureLand.git
2. Rub the repository:  
   ```sh
   mvn spring-boot:run

## API Endpoints  

### Customer API (`/api/customers`)  
- **`POST /addCustomer`** - Add a new customer. 
- **`POST /updateCustomer/{id}`** - Update an existing customer.
- **`POST /addCustomerCouponCode/{phoneNumber}/{discountPercentage}`** - Assign a discount to a customer.  
- **`GET /phone/{phoneNumber}`** - Retrieve customer details by phone number.
- **`GET /getCustomers`** - Retrieve all customers.

### Product API (`/api/products`)  
- **`POST /addProduct`** - Add a new product
- **`POST /updateProduct/{id}`** - Update an existing product.  
- **`GET /getProductsByDetails`** - Retrieve products by these query parameter hsnNumber, productCode, color, manufacturer, status .  
- **`GET /code/{productCode}`** - Retrieve products by product code.  
- **`GET /getProducts`** - Get all available products.  

### Billing API (`/api/billings`)  
- **`POST /`** - Create a new billing entry.
- **`GET /getBillings`** - Get all available billings. 
- **`DELETE /cancel/{billingId}`** - Cancel a billing if required.  


