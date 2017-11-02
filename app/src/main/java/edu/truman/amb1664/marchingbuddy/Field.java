package edu.truman.amb1664.marchingbuddy;

/**
 * @author Andrew Brogan
 * @since 10/31/17
 */
class Field {
    private int fieldType;
    private int sideType;
    private int hashType;

    /**
     * Constructs Field object, defining the types of the field
     *
     * @param fieldType 0 = High School, 1 = NCAA
     * @param sideType  0 = Side 1/Side 2, 1 = Left/Right
     * @param hashType  0 = Front/Back, 1 = Home/Visitor
     */
    Field(int fieldType, int sideType, int hashType) {
        super();
        this.fieldType = fieldType;
        this.sideType = sideType;
        this.hashType = hashType;
    }

    // Getters
    int getFieldType() {
        return fieldType;
    }

    int getSideType() {
        return sideType;
    }

    int getHashType() {
        return hashType;
    }
}
