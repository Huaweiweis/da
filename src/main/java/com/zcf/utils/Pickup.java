package com.zcf.utils;

import java.math.BigDecimal;
import java.util.*;

public class Pickup {

	public static void main(String[] args) {
		// 1.11, 1.68, 0.60, 6.16, 0.45

		/*List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("1.18"));
		list.add(new BigDecimal("1.11"));
		list.add(new BigDecimal("0.45"));
		list.add(new BigDecimal("0.60"));
		list.add(new BigDecimal("6.66"));*/
		 List<BigDecimal> list = math(new BigDecimal("10"), 10);
		System.out.println(list);
		Collections.sort(list, new Comparator<BigDecimal>() {
			@Override
			public int compare(BigDecimal o1, BigDecimal o2) {
				if (Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
				// 比较数和被比较数都为豹子时处理
					return Pickup.getlast(o1) - Pickup.getlast(o2);
				}
				if (Pickup.is_baozi(o1) && !Pickup.is_baozi(o2)) {
					// 比较数是豹子和被比较数不为豹子
					return 1;
				}
				if (!Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
					// 比较数不为豹子 和 被比较数为豹子
					return -1;
				}
				Integer result = Pickup.getsumlast(o1) - Pickup.getsumlast(o2);
					// 末两位求和结果相等 结果处理 比较首位
					return result == 0 ? Pickup.getfirst(o1) - Pickup.getfirst(o2) : result;
				}
			});
			System.out.println(list+"===============");
			Collections.reverse(list);
		System.out.println(list);
	}

	/**
	 * 判断是否豹子
	 * 
	 * @param num
	 * @return
	 */
	public static boolean is_baozi(BigDecimal num) {
		String[] numArr = num.toString().replace(".", "").split("");
		return numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2]);
	}

	/**
	 * 获取最后一位
	 * 
	 * @param num
	 * @return
	 */
	public static Integer getlast(BigDecimal num) {
		String str = num.toString();
		return Integer.parseInt(str.substring(str.length() - 1, str.length()));
	}

	/**
	 * 
	 * 获取首位
	 * 
	 * @param num
	 * @return
	 */
	public static Integer getfirst(BigDecimal num) {
		String str = num.toString();
		return Integer.parseInt(str.substring(0, 1));
	}

	/**
	 * 求和最后一位
	 * 
	 * @param num
	 * @return
	 */
	public static Integer getsumlast(BigDecimal num) {
		String str = num.toString();
		String[] str_Arr = str.substring(str.lastIndexOf('.') + 1, str.length()).split("");
		Integer index_1 = Integer.parseInt(str_Arr[0]);
		Integer index_2 = Integer.parseInt(str_Arr[1]);
		String sum = (index_1 + index_2) + "";
		return Integer.parseInt(sum.substring(sum.length() - 1, sum.length()));
	}

	/**
	 * 计算每人获得红包金额;最小每人0.01元
	 * 
	 * @param mmm
	 *            红包总额
	 * @param number
	 *            人数
	 * @return
	 */
	public static List<BigDecimal> math(BigDecimal mmm, int number) {
		if (mmm.doubleValue() < number * 0.01) {
			return null;
		}
		Random random = new Random();
		// 金钱，按分计算 10块等于 1000分
		int money = mmm.multiply(BigDecimal.valueOf(100)).intValue();
		// 随机数总额
		double count = 0;
		// 每人获得随机点数
		double[] arrRandom = new double[number];
		// 每人获得钱数
		List<BigDecimal> arrMoney = new ArrayList<BigDecimal>(number);
		// 循环人数 随机点
		for (int i = 0; i < arrRandom.length; i++) {
			int r = random.nextInt((number) * 99) + 1;
			count += r;
			arrRandom[i] = r;
		}
		// 计算每人拆红包获得金额
		int c = 0;
		for (int i = 0; i < arrRandom.length; i++) {
			// 每人获得随机数相加 计算每人占百分比
			Double x = new Double(arrRandom[i] / count);
			// 每人通过百分比获得金额
			int m = (int) Math.floor(x * money);
			// 如果获得 0 金额，则设置最小值 1分钱
			if (m == 0) {
				m = 1;
			}
			// 计算获得总额
			c += m;
			// 如果不是最后一个人则正常计算
			if (i < arrRandom.length - 1) {
				arrMoney.add(new BigDecimal(m).setScale(2).divide(new BigDecimal(100)));
			} else {
				// 如果是最后一个人，则把剩余的钱数给最后一个人
				arrMoney.add(new BigDecimal(money - c + m).setScale(2).divide(new BigDecimal(100).setScale(2)));
			}
		}
		// 随机打乱每人获得金额
		Collections.shuffle(arrMoney);
		return arrMoney;
	}
}
