/*
 * Teragrep RFC5424 frame library for Java (rlo_06)
 * Copyright (C) 2022-2024 Suomen Kanuuna Oy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 * Additional permission under GNU Affero General Public License version 3
 * section 7
 *
 * If you modify this Program, or any covered work, by linking or combining it
 * with other code, such other code is not for that reason alone subject to any
 * of the requirements of the GNU Affero GPL version 3 as long as this Program
 * is the same Program as licensed from Suomen Kanuuna Oy without any additional
 * modifications.
 *
 * Supplemented terms under GNU Affero General Public License version 3
 * section 7
 *
 * Origin of the software must be attributed to Suomen Kanuuna Oy. Any modified
 * versions must be marked as "Modified version of" The Program.
 *
 * Names of the licensors and authors may not be used for publicity purposes.
 *
 * No rights are granted for use of trade names, trademarks, or service marks
 * which are in The Program if any.
 *
 * Licensee must indemnify licensors and authors for any liability that these
 * contractual assumptions impose on licensors and authors.
 *
 * To the extent this program is licensed as part of the Commercial versions of
 * Teragrep, the applicable Commercial License may apply to this file if you as
 * a licensee so wish it.
 */
package com.teragrep.rlo_06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcIdTest {

    @Test
    public void parseTest() {
        Fragment procId = new Fragment(128, new ProcIdFunction());

        String input = "cade00f0-3260-4b88-ab61-d644a75dfbbb ";

        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(StandardCharsets.US_ASCII));

        Stream stream = new Stream();
        stream.setInputStream(bais);

        procId.accept(stream);

        Assertions.assertEquals("cade00f0-3260-4b88-ab61-d644a75dfbbb", procId.toString());
    }

    @Test
    public void emptyProcIdTest() {
        Fragment procId = new Fragment(128, new ProcIdFunction());

        String input = "";

        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(StandardCharsets.US_ASCII));

        assertThrows(ParseException.class, () -> {
            Stream stream = new Stream();
            stream.setInputStream(bais);
            procId.accept(stream);
            procId.toString();
        });
    }

    @Test
    public void tooLongProcIdTest() {
        Fragment procId = new Fragment(128, new ProcIdFunction());

        String input = new String(new char[256]).replace('\0', 'x');

        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(StandardCharsets.US_ASCII));

        assertThrows(ProcIdParseException.class, () -> {
            Stream stream = new Stream();
            stream.setInputStream(bais);
            procId.accept(stream);
            procId.toString();
        });
    }
}
