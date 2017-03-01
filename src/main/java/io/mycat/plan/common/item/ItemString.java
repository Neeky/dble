package io.mycat.plan.common.item;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;

import io.mycat.plan.common.field.Field;
import io.mycat.plan.common.time.MySQLTime;

public class ItemString extends ItemBasicConstant {
	private String value;

	public ItemString(String value) {
		this.value = value;
		maxLength = value.length();
		fixed = true;
		decimals = NOT_FIXED_DEC;
	}

	@Override
	public ItemType type() {
		return ItemType.STRING_ITEM;
	}

	@Override
	public BigDecimal valReal() {
		BigDecimal decValue = BigDecimal.ZERO;
		try {
			decValue = new BigDecimal(value);
		} catch (Exception e) {
			logger.warn("convert string to decimal exception!", e);
		}
		return decValue;
	}

	@Override
	public BigInteger valInt() {
		BigInteger intValue = BigInteger.ZERO;
		try {
			intValue = new BigInteger(value);
		} catch (Exception e) {
			logger.warn("convert string to int exception!", e);
		}
		return intValue;
	}

	@Override
	public String valStr() {
		return value;
	}

	@Override
	public BigDecimal valDecimal() {
		return valDecimalFromString();
	}

	@Override
	public boolean getDate(MySQLTime ltime, long fuzzydate) {
		return getDateFromString(ltime, fuzzydate);
	}

	@Override
	public boolean getTime(MySQLTime ltime) {
		return getTimeFromString(ltime);
	}

	@Override
	public ItemResult resultType() {
		return ItemResult.STRING_RESULT;
	}

	@Override
	public FieldTypes fieldType() {
		return FieldTypes.MYSQL_TYPE_VARCHAR;
	}

	@Override
	public int hashCode() {
		if (value == null)
			return 0;
		else
			return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemFloat))
			return false;
		ItemString other = (ItemString) obj;
		if (value == null || other.value == null)
			return value == null && other.value == null;
		else
			return value.equals(other.value);
	}

	@Override
	public SQLExpr toExpression() {
		return new SQLCharExpr(value);//LiteralString(null, value, false);
	}

	@Override
	protected Item cloneStruct(boolean forCalculate, List<Item> calArgs, boolean isPushDown, List<Field> fields) {
		return new ItemString(value);
	}

}