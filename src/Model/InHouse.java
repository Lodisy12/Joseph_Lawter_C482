package Model;

/**
 * Extends Part class, creates a part that was made in house.
 * @author Joseph Lawter
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * main constructor that extends the constructor from Part.
     * @param id The ID number of the part
     * @param name The name of the part
     * @param price The price of the part
     * @param stock How many of that part we have on hand
     * @param min The minimum number of parts we can have at a time
     * @param max The maximum number of parts we can have at a time
     * @param machineId The ID number of the machine that fabricated the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId Integer that replaces the current Machine ID
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     *
     * @return this.machineId returns the id of the machine used to fabricate the item
     */
    public int getMachineId(){
        return this.machineId;
    }

}
