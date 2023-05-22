module isAscii {
    requires jdk.incubator.vector;
    requires jmh.core;
    requires jdk.unsupported;
    exports com.github.pkpkpk.isAscii;
    opens com.github.pkpkpk.isAscii.jmh_generated to jmh.core;
}