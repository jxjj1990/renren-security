package io.renren.entity;

/**
 * [特性实体]
 *
 * @authorZJS
 * @create 2017-05-05 11:40
 */
public class ImpliedFeatureEntity {
    /**
     * 要的设备特性名称。
     */
    private String feature;

    /**
     * 表明所需特性的内容。
     */
    private String implied;

    public ImpliedFeatureEntity() {
        super();
    }

    public ImpliedFeatureEntity(String feature, String implied) {
        super();
        this.feature = feature;
        this.implied = implied;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getImplied() {
        return implied;
    }

    public void setImplied(String implied) {
        this.implied = implied;
    }

    @Override
    public String toString() {
        return "Feature [feature=" + feature + ", implied=" + implied + "]";
    }

}
