package com.b.simple.design.business.customer;

import java.math.BigDecimal;
import java.util.List;

import com.b.simple.design.business.exception.DifferentCurrenciesException;
import com.b.simple.design.model.customer.Amount;
import com.b.simple.design.model.customer.AmountImpl;
import com.b.simple.design.model.customer.Currency;
import com.b.simple.design.model.customer.Product;

public class CustomerBOImpl implements CustomerBO {

	@Override
	public Amount getCustomerProductsSum(List<Product> products)
			throws DifferentCurrenciesException {

		if (products.size() == 0)
			return new AmountImpl(BigDecimal.ZERO, Currency.EURO);

		if (!doAllProductsHaveSameCurrency(products)) {
			throw new DifferentCurrenciesException();
		}

		return calculateSumOfProducts(products);
	}

	private static AmountImpl calculateSumOfProducts(List<Product> products) {

		Currency firstProductCurrency = products.get(0).getAmount()
				.getCurrency();

		BigDecimal sum =  products.stream()
				.map(product -> product.getAmount().getValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new AmountImpl(sum, firstProductCurrency);
	}

	private static boolean doAllProductsHaveSameCurrency(List<Product> products) {

		Currency firstProductCurrency = products.get(0).getAmount()
				.getCurrency();

		return products.stream()
				.map(product -> product.getAmount().getCurrency())
				.allMatch(currency -> currency.equals(firstProductCurrency));
	}

	public Amount getCustomerProductsSum_Original(List<Product> products)
			throws DifferentCurrenciesException {
		BigDecimal temp = BigDecimal.ZERO;

		if (products.size() == 0)
			return new AmountImpl(temp, Currency.EURO);

		// Throw Exception If Any of the product has a currency different from
		// the first product
		Currency firstProductCurrency = products.get(0).getAmount()
				.getCurrency();

		for (Product product : products) {
			boolean currencySameAsFirstProduct = product.getAmount()
					.getCurrency().equals(firstProductCurrency);
			if (!currencySameAsFirstProduct) {
				throw new DifferentCurrenciesException();
			}
		}

		// Calculate Sum of Products
		for (Product product : products) {
			temp = temp.add(product.getAmount().getValue());
		}

		// Create new product
		return new AmountImpl(temp, firstProductCurrency);
	}
}