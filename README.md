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
   git clone https://github.com/your-username/FurnitureLand.git
2. Rub the repository:  
   ```sh
   mvn spring-boot:run

## API Endpoints  

### Customer API (`/api/customers`)  
- **`POST /addCustomer`** - Add a new customer.  
- **`POST /addCustomerCouponCode/{phoneNumber}/{couponCode}/{discountPercentage}`** - Assign a coupon to a customer.  
- **`GET /phone/{phoneNumber}`** - Retrieve customer details.  

### Product API (`/api/products`)  
- **`POST /`** - Add a new product.  
- **`GET /hsn/{hsn}`** - Retrieve products by HSN.  
- **`GET /code/{productCode}`** - Retrieve a product by code.  
- **`GET /getProducts`** - Get all available products.  

### Billing API (`/api/billings`)  
- **`POST /`** - Create a new bill with applicable discounts.  
- **`DELETE /cancel/{billingId}`** - Cancel a bill if required.  


