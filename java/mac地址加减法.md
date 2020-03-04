# mac 加减法

```

    public static void main(String[] args) throws Exception {
        System.err.println(getMac("ff:ff:ff:ff:ff:01",14));
    }

    /***
     * @author wxm
     * @param mac 地址 ff:ff:ff:ff:ff:01
     * @param num 整数为加 负数为减
     * @return ff:ff:ff:ff:ff:0f
     */
    public static String getMac(String mac, int num) {
        mac = checkMac(mac.replace(":",""));
        Long longMac = Long.parseLong(mac, 16);
        String tempMac = Long.toHexString(longMac + num).toLowerCase(Locale.getDefault());
        if(tempMac.length() != 12) {
            throw new RuntimeException("new mac is error");
        }
        return getMac(tempMac);
    }

    private static String getMac(String str) {
        if (str.length() <=2) {
            return str;
        }
        return str.substring(0, 2) + ":" + getMac(str.substring(2));
    }

    private static String checkMac(String mac) {
        Pattern p = Pattern.compile("^[0-9a-fA-F]{12}$");
        Matcher matcher = p.matcher(mac);
        if (!matcher.matches()) {
            throw new RuntimeException("Mac is error");
        }
        return mac;
    }
    

```
