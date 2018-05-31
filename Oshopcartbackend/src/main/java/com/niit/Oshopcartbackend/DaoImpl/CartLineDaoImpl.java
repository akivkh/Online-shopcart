package com.niit.Oshopcartbackend.DaoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.Oshopcartbackend.Dao.CartLineDao;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;

@Repository("cartLineDao")

public class CartLineDaoImpl implements CartLineDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public CartLine get(int id) {
		
		return sessionFactory.getCurrentSession().get(CartLine.class, id);
	}

	@Override
	public boolean add(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().persist(cartLine);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().update(cartLine);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		return false;
	}
	}
	@Override
	public boolean delete(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().delete(cartLine);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<CartLine> list(int cartId) {
		String query = "FROM CartLine WHERE cartId= :cartrId";
		return sessionFactory
				       .getCurrentSession()
				             .createQuery(query, CartLine.class)
				                .setParameter("cartId", cartId)
				                   .getResultList();

	}

	@Override
	public List<CartLine> listAvailable(int cartId) {
		String query = "FROM CartLine WHERE cartId= :cartrId AND available = :available";
		return sessionFactory
				       .getCurrentSession()
				             .createQuery(query, CartLine.class)
				                .setParameter("cartId", cartId)
				                 .setParameter("available", true)
				                   .getResultList();
	}

	@Override
	public CartLine getByCartAndProduct(int cartId, int productId) {
		String query = "FROM CartLine WHERE cartId= :cartrId AND product.id = :productId";
		try {
		return sessionFactory
			       .getCurrentSession()
			             .createQuery(query, CartLine.class)
			                .setParameter("cartId", cartId)
			                 .setParameter("productId", productId)
			                   .getSingleResult();
	}
		catch(Exception ex) {
			return null;
		}
	} 

	// related to the cart
	@Override 
	public boolean updateCart(Cart cart) {
		try {
			sessionFactory.getCurrentSession().update(cart);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
	}
	}


