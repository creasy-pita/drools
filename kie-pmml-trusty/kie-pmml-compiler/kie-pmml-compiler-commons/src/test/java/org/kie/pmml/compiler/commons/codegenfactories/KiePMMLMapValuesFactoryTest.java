/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.pmml.compiler.commons.codegenfactories;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.dmg.pmml.DerivedField;
import org.dmg.pmml.MapValues;
import org.dmg.pmml.PMML;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.pmml.commons.model.expressions.KiePMMLFieldColumnPair;
import org.kie.pmml.commons.model.expressions.KiePMMLInlineTable;
import org.kie.pmml.commons.model.expressions.KiePMMLMapValues;
import org.kie.pmml.commons.model.expressions.KiePMMLRow;
import org.kie.pmml.compiler.commons.utils.JavaParserUtils;
import org.kie.pmml.compiler.commons.utils.KiePMMLUtil;

import static org.junit.Assert.assertTrue;
import static org.kie.pmml.compiler.commons.testutils.CodegenTestUtils.commonValidateCompilationWithImports;
import static org.kie.test.util.filesystem.FileUtils.getFileInputStream;

public class KiePMMLMapValuesFactoryTest {

    private static final String TRANSFORMATIONS_SAMPLE = "TransformationsSample.pmml";
    private static final String MAPVALUED = "mapvalued";
    private static MapValues MAPVALUES;

    @BeforeClass
    public static void setup() throws Exception {
        PMML pmmlModel = KiePMMLUtil.load(getFileInputStream(TRANSFORMATIONS_SAMPLE), TRANSFORMATIONS_SAMPLE);
        DerivedField mapValued = pmmlModel.getTransformationDictionary()
                .getDerivedFields()
                .stream()
                .filter(derivedField -> MAPVALUED.equals(derivedField.getName().getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Missing derived field " + MAPVALUED));
        MAPVALUES = ((MapValues) mapValued.getExpression());
    }

    @Test
    public void getMapValuesVariableDeclaration() {
        String variableName = "variableName";
        BlockStmt retrieved = KiePMMLMapValuesFactory.getMapValuesVariableDeclaration(variableName,
                                                                                          MAPVALUES);
        Statement expected = JavaParserUtils.parseBlock(String.format("{\n" +
                                                                              "    KiePMMLFieldColumnPair " +
                                                                              "%1$s_0 = new " +
                                                                              "KiePMMLFieldColumnPair(\"BAND\", " +
                                                                              "Collections.emptyList(), \"band\");\n" +
                                                                              "    KiePMMLFieldColumnPair " +
                                                                              "%1$s_1 = new " +
                                                                              "KiePMMLFieldColumnPair(\"STATE\", " +
                                                                              "Collections.emptyList(), \"state\");\n" +
                                                                              "    Map<String, Object> " +
                                                                              "%1$s_InlineTable_0_columnValues = Stream.of(new Object[][] { { \"band\", \"1\" }, { \"state\", \"MN\" }, { \"out\", \"10000\" } }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));\n" +
                                                                              "    KiePMMLRow " +
                                                                              "%1$s_InlineTable_0 = new " +
                                                                              "KiePMMLRow" +
                                                                              "(%1$s_InlineTable_0_columnValues);\n" +
                                                                              "    Map<String, Object> " +
                                                                              "%1$s_InlineTable_1_columnValues = Stream.of(new Object[][] { { \"band\", \"1\" }, { \"state\", \"IL\" }, { \"out\", \"12000\" } }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));\n" +
                                                                              "    KiePMMLRow " +
                                                                              "%1$s_InlineTable_1 = new " +
                                                                              "KiePMMLRow" +
                                                                              "(%1$s_InlineTable_1_columnValues);\n" +
                                                                              "    Map<String, Object> " +
                                                                              "%1$s_InlineTable_2_columnValues = Stream.of(new Object[][] { { \"band\", \"1\" }, { \"state\", \"NY\" }, { \"out\", \"20000\" } }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));\n" +
                                                                              "    KiePMMLRow " +
                                                                              "%1$s_InlineTable_2 = new " +
                                                                              "KiePMMLRow" +
                                                                              "(%1$s_InlineTable_2_columnValues);\n" +
                                                                              "    Map<String, Object> " +
                                                                              "%1$s_InlineTable_3_columnValues = Stream.of(new Object[][] { { \"band\", \"2\" }, { \"state\", \"MN\" }, { \"out\", \"20000\" } }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));\n" +
                                                                              "    KiePMMLRow " +
                                                                              "%1$s_InlineTable_3 = new " +
                                                                              "KiePMMLRow" +
                                                                              "(%1$s_InlineTable_3_columnValues);\n" +
                                                                              "    Map<String, Object> " +
                                                                              "%1$s_InlineTable_4_columnValues = Stream.of(new Object[][] { { \"band\", \"2\" }, { \"state\", \"IL\" }, { \"out\", \"23000\" } }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));\n" +
                                                                              "    KiePMMLRow " +
                                                                              "%1$s_InlineTable_4 = new " +
                                                                              "KiePMMLRow" +
                                                                              "(%1$s_InlineTable_4_columnValues);\n" +
                                                                              "    Map<String, Object> " +
                                                                              "%1$s_InlineTable_5_columnValues = Stream.of(new Object[][] { { \"band\", \"2\" }, { \"state\", \"NY\" }, { \"out\", \"30000\" } }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1]));\n" +
                                                                              "    KiePMMLRow " +
                                                                              "%1$s_InlineTable_5 = new " +
                                                                              "KiePMMLRow" +
                                                                              "(%1$s_InlineTable_5_columnValues);\n" +
                                                                              "    KiePMMLInlineTable " +
                                                                              "%1$s_InlineTable = new " +
                                                                              "KiePMMLInlineTable" +
                                                                              "(\"%1$s_InlineTable\", " +
                                                                              "Collections.emptyList(), Arrays.asList" +
                                                                              "(%1$s_InlineTable_0, " +
                                                                              "%1$s_InlineTable_1, " +
                                                                              "%1$s_InlineTable_2, " +
                                                                              "%1$s_InlineTable_3, " +
                                                                              "%1$s_InlineTable_4, " +
                                                                              "%1$s_InlineTable_5));\n" +
                                                                              "    KiePMMLMapValues %1$s = " +
                                                                              "KiePMMLMapValues.builder" +
                                                                              "(\"%1$s\", Collections" +
                                                                              ".emptyList(), \"out\")" +
                                                                              ".withDefaultValue(null)" +
                                                                              ".withMapMissingTo(null).withDataType" +
                                                                              "(org.kie.pmml.api.enums.DATA_TYPE" +
                                                                              ".INTEGER).withKiePMMLInlineTable" +
                                                                              "(%1$s_InlineTable)" +
                                                                              ".withKiePMMLFieldColumnPairs(Arrays" +
                                                                              ".asList(%1$s_0, " +
                                                                              "%1$s_1)).build();\n" +
                                                                              "}", variableName));
        assertTrue(JavaParserUtils.equalsNode(expected, retrieved));
        List<Class<?>> imports = Arrays.asList(Arrays.class, Collections.class, Collectors.class, KiePMMLFieldColumnPair.class, KiePMMLInlineTable.class, KiePMMLMapValues.class, KiePMMLRow.class, Map.class, Stream.class);
        commonValidateCompilationWithImports(retrieved, imports);
    }
}