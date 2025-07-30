public class L2Cache {
    public Word32 lookupAddress = new Word32();
    public Word32 value = new Word32();
    public Word32 returnValue = new Word32();
    public Word32 firstAddress = new Word32();
    public Word32 address1 = new Word32();
    public Word32 address2 = new Word32();
    public Word32 address3 = new Word32();
    public Word32 address4 = new Word32();
    public Word32[] cache1 = new Word32[8];
    public Word32[] cache2 = new Word32[8];
    public Word32[] cache3 = new Word32[8];
    public Word32[] cache4 = new Word32[8];
    public Word32 mask = new Word32();
    public boolean registers = false;
    int index = 0;
    InstructionCache cache = new InstructionCache();

    public boolean read(InstructionCache cache) {
        fromInt(7, mask);
        mask.not(mask);
        int lookUpAddressInt = addressAsInt(lookupAddress);
        int addressStart = addressAsInt(address1);
        if (addressStart == 0 && lookUpAddressInt == 0) {
            firstAddress.copy(address1);
        }
        if (cache1[0] != null && (lookUpAddressInt >= addressAsInt(address1) && lookUpAddressInt < addressAsInt(address1) + 7)) {
            loadIntoCache(cache, cache1);
            address1.copy(cache.firstAddress);
            return true;
        } else if (cache2[0] != null && (lookUpAddressInt >= addressAsInt(address2) && lookUpAddressInt < addressAsInt(address2) + 7)) {
            loadIntoCache(cache, cache2);
            address2.copy(cache.firstAddress);
            return true;
        } else if (cache3[0] != null && (lookUpAddressInt >= addressAsInt(address3) && lookUpAddressInt < addressAsInt(address3) + 7)) {
            loadIntoCache(cache, cache3);
            address3.copy(cache.firstAddress);
            return true;
        } else if (cache4[0] != null && (lookUpAddressInt >= addressAsInt(address4) && lookUpAddressInt < addressAsInt(address4) + 7)) {
            loadIntoCache(cache, cache4);
            address4.copy(cache.firstAddress);
            return true;
        } else {
            return false;
        }
    }

    public void write(Memory mem) {
        int lookUpAddressInt = addressAsInt(lookupAddress);
        int offset = 0;
        Word32 temp2 = new Word32();
        while (true) {
            mask.and(lookupAddress, temp2);
            if (lookUpAddressInt >= addressAsInt(address1) && lookUpAddressInt < addressAsInt(address1) + 7) {
                offset = lookUpAddressInt - addressAsInt(address1);
                if (cache1[offset] == null) {
                    lookupAddress.copy(mem.address);
                    mem.read();
                    cache1[addressAsInt(lookupAddress) - addressAsInt(address1)] = new Word32();
                    mem.value.copy(cache1[offset]);
                } else {
                    cache1[offset].copy(returnValue);
                }
                return;
            } else if (lookUpAddressInt >= addressAsInt(address2) && lookUpAddressInt < addressAsInt(address2) + 7) {
                offset = lookUpAddressInt - addressAsInt(address2);
                if (cache2[offset] == null) {
                    lookupAddress.copy(mem.address);
                    mem.read();
                    cache2[addressAsInt(lookupAddress) - addressAsInt(address2)] = new Word32();
                    if (registers) {
                        value.copy(cache2[offset]);
                    } else {
                        mem.value.copy(cache2[offset]);
                    }
                } else {
                    cache2[offset].copy(returnValue);
                }
                return;
            } else if (lookUpAddressInt >= addressAsInt(address3) &&  lookUpAddressInt < addressAsInt(address3) + 7) {
                offset = lookUpAddressInt - addressAsInt(address3);
                if (cache3[offset] == null) {
                    lookupAddress.copy(mem.address);
                    mem.read();
                    cache3[addressAsInt(lookupAddress) - addressAsInt(address3)] = new Word32();
                    mem.value.copy(cache3[offset]);
                } else {
                    cache3[offset].copy(returnValue);
                }
                return;
            } else if (lookUpAddressInt >= addressAsInt(address4) && lookUpAddressInt < addressAsInt(address4) + 7) {
                offset = lookUpAddressInt - addressAsInt(address4);
                if (cache4[offset] == null) {
                    lookupAddress.copy(mem.address);
                    mem.read();
                    cache4[addressAsInt(lookupAddress) - addressAsInt(address4)] = new Word32();
                    mem.value.copy(cache4[offset]);
                } else {
                    cache4[offset].copy(returnValue);
                }
                return;
            } else if (isCacheEmpty(cache1)) {
                temp2.copy(address1);
                lookupAddress.copy(mem.address);
                mem.read();
                cache1[addressAsInt(lookupAddress) - addressAsInt(address1)] = new Word32();
                if (registers) {
                    value.copy(cache1[addressAsInt(lookupAddress) - addressAsInt(address1)]);
                } else {
                    mem.value.copy(cache1[addressAsInt(lookupAddress) - addressAsInt(address1)]);
                }
                return;
            } else if (isCacheEmpty(cache2)) {
                temp2.copy(address2);
                lookupAddress.copy(mem.address);
                mem.read();
                cache2[addressAsInt(lookupAddress) - addressAsInt(address2)] = new Word32();
                if (registers) {
                    value.copy(cache2[addressAsInt(lookupAddress) - addressAsInt(address2)]);
                } else {
                    mem.value.copy(cache2[addressAsInt(lookupAddress) - addressAsInt(address2)]);
                }
                return;
            } else if (isCacheEmpty(cache3)) {
                temp2.copy(address3);
                lookupAddress.copy(mem.address);
                mem.read();
                cache3[addressAsInt(lookupAddress) - addressAsInt(address3)] = new Word32();
                if (registers) {
                    value.copy(cache3[addressAsInt(lookupAddress) - addressAsInt(address3)]);
                } else {
                    mem.value.copy(cache3[addressAsInt(lookupAddress) - addressAsInt(address3)]);
                }
                return;
            } else if (isCacheEmpty(cache4)) {
                temp2.copy(address4);
                lookupAddress.copy(mem.address);
                mem.read();
                cache4[addressAsInt(lookupAddress) - addressAsInt(address4)] = new Word32();
                if (registers) {
                    value.copy(cache4[addressAsInt(lookupAddress) - addressAsInt(address4)]);
                } else {
                    mem.value.copy(cache4[addressAsInt(lookupAddress) - addressAsInt(address4)]);
                }
                return;
            } else {
                index++;
                if (index == 1) {
                    cache1 = new Word32[8];
                } else if (index == 2) {
                    cache2 = new Word32[8];
                } else if (index == 3) {
                    cache3 = new Word32[8];
                } else if (index == 4) {
                    cache4 = new Word32[8];
                } else {
                    index = 0;
                }
            }
        }
    }

    public void loadIntoCache(InstructionCache cache, Word32[] l2Cache) {
        for (int i = 0; i < 8; i++) {
            cache.cache[i] = new Word32();
            l2Cache[i].copy(cache.cache[i]);
        }
    }

    public boolean isCacheEmpty(Word32[] l2Cache) {
        for (int i = 0; i < l2Cache.length; i++) {
            if (l2Cache[i] != null) {
                return false;
            }
        }
        return true;
    }


    public void loadIntoL2Cache(Memory mem) {
        Word32 temp = new Word32();
        fromInt(1,temp);
        Word32 currentAddress = new Word32();
        while (true) {
            lookupAddress.and(mask, currentAddress);
            if (isCacheEmpty(cache1)) {
                for (int i = 0; i < 8; i++) {
                    currentAddress.copy(mem.address);
                    mem.read();
                    cache1[i] = new Word32();
                    mem.value.copy(cache1[i]);
                    Adder.add(currentAddress, temp, currentAddress);
                }
                lookupAddress.copy(address1);
                return;
            } else if (isCacheEmpty(cache2)) {
                for (int i = 0; i < 8; i++) {
                    currentAddress.copy(mem.address);
                    mem.read();
                    cache2[i] = new Word32();
                    mem.value.copy(cache2[i]);
                    Adder.add(currentAddress, temp, currentAddress);
                }
                lookupAddress.copy(address2);
            } else if (isCacheEmpty(cache3)) {
                for (int i = 0; i < 8; i++) {
                    currentAddress.copy(mem.address);
                    mem.read();
                    cache3[i] = new Word32();
                    mem.value.copy(cache3[i]);
                    Adder.add(currentAddress, temp, currentAddress);
                }
                lookupAddress.copy(address3);
                return;
            } else if (isCacheEmpty(cache4)) {
                for (int i = 0; i < 8; i++) {
                    currentAddress.copy(mem.address);
                    mem.read();
                    cache4[i] = new Word32();
                    mem.value.copy(cache4[i]);
                    Adder.add(currentAddress, temp, currentAddress);
                }
                lookupAddress.copy(address4);
                return;
            } else {
                index++;
                if (index == 1) {
                    cache1 = new Word32[8];
                } else if (index == 2) {
                    cache2 = new Word32[8];
                } else if (index == 3) {
                    cache3 = new Word32[8];
                } else if (index == 4) {
                    cache4 = new Word32[8];
                } else {
                    index = 0;
                }
            }
        }
    }

    public static void fromInt(int value, Word32 result) {
        int temp = value;
        int i = 0;
        if (value < 0){
            value = (value * -1) - 1;
        }
        for (int j = 0; j < 32; j++) {
            result.setBitN(j, new Bit(false));
        }
        while (value > 0){
            int remainder = value % 2;
            result.setBitN(31-i, new Bit(remainder == 1));
            value = value / 2;
            i++;
        }
        if (temp < 0) {
            result.not(result);
        }
    }

    public int addressAsInt(Word32 address) {
        Bit result = new Bit(false);
        int valueAsInt = 0;
        for(int i = 0; i < 32; i++) {
            address.getBitN(31-i,result);
            if(result.getValue() == Bit.boolValues.TRUE){
                valueAsInt += Math.pow(2, i);
            }
        }
        if(valueAsInt > 999){
            throw new ArithmeticException();
        } else {
            return valueAsInt;
        }
    }
}