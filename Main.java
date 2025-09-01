enum VehicleType{
    Mobil,
    Motor,
    Truck
}

class Kendaraan{
    String brand;
    int year;
    VehicleType type;
    double harga;

    Kendaraan(String brand, int year, VehicleType type, double harga){
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.harga = harga;
    }

    void showDetail(){
        System.out.println("---------------");
        System.out.println("Brand: " + brand);
        System.out.println("Year"+ year);
        System.out.println("Type: " + type);
        System.out.println("Harga: " + harga);
    }
}
    class Customer{
        String name;
        Kendaraan kendaraan;

        Customer(String name, Kendaraan kendaraan){
            this.name = name;
            this.kendaraan = kendaraan;
        }

        double getTotalPrice(){
            return kendaraan.harga;
        }

        void showDetail(){
            System.out.println("-----------------------");
            System.out.println("Customer Name: " + name);
            kendaraan.showDetail();
            System.out.println("Total Price: " + getTotalPrice());
            System.out.println();
        }
    }

    public class Main{
        public static void main(String[] args){
            Kendaraan kendaraanSupraBapak = new Kendaraan("Honda Supra", 1998, VehicleType.Motor, 3000);
            Kendaraan kendaraanKaler = new Kendaraan("VW Beetle", 1998, VehicleType.Mobil, 20000);
            Kendaraan kendaraanGuede = new Kendaraan("Isuzu Giga", 2011, VehicleType.Truck, 300000);

            Customer c1 = new Customer("Bapak joko", kendaraanSupraBapak);
            Customer c2 = new Customer("Ibu Siti", kendaraanKaler);
            Customer c3 = new Customer("Bapak Budi", kendaraanGuede);

            c1.showDetail();
            c2.showDetail();
            c3.showDetail();

        }
    }


