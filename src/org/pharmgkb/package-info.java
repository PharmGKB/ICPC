@TypeDefs({
    @TypeDef(name = "valueType",
        typeClass = HibernateExtendedEnumType.class,
        parameters = {
            @Parameter(
                name  = "enumClass",
                value = "org.pharmgkb.Value"),
            @Parameter(
                name  = "identifierMethod",
                value = "getId"),
            @Parameter(
                name  = "valueOfMethod",
                value = "lookupById")
        }
    ),
    @TypeDef(name = "alcoholStatusType",
        typeClass = HibernateExtendedEnumType.class,
        parameters = {
            @Parameter(
                name  = "enumClass",
                value = "org.pharmgkb.AlcoholStatus"),
            @Parameter(
                name  = "identifierMethod",
                value = "getId"),
            @Parameter(
                name  = "valueOfMethod",
                value = "lookupById")
        }
    ),
    @TypeDef(name = "diabetesStatusType",
        typeClass = HibernateExtendedEnumType.class,
        parameters = {
            @Parameter(
                name  = "enumClass",
                value = "org.pharmgkb.DiabetesStatus"),
            @Parameter(
                name  = "identifierMethod",
                value = "getId"),
            @Parameter(
                name  = "valueOfMethod",
                value = "lookupById")
        }
    ),
    @TypeDef(name = "genderType",
        typeClass = HibernateExtendedEnumType.class,
        parameters = {
            @Parameter(
                name  = "enumClass",
                value = "org.pharmgkb.Gender"),
            @Parameter(
                name  = "identifierMethod",
                value = "getId"),
            @Parameter(
                name  = "valueOfMethod",
                value = "lookupById")
        }
    ),
    @TypeDef(name = "sampleSourceType",
        typeClass = HibernateExtendedEnumType.class,
        parameters = {
            @Parameter(
                name  = "enumClass",
                value = "org.pharmgkb.SampleSource"),
            @Parameter(
                name  = "identifierMethod",
                value = "getId"),
            @Parameter(
                name  = "valueOfMethod",
                value = "lookupById")
        }
    )
})

package org.pharmgkb;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.pharmgkb.hibernate.HibernateExtendedEnumType;