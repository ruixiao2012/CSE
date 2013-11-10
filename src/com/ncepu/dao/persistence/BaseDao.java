package com.ncepu.dao.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

public abstract class BaseDao<T> {
	private Class<T> eClass;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		eClass = (Class<T>) ((ParameterizedType) (this.getClass()
				.getGenericSuperclass())).getActualTypeArguments()[0];
	}

	/**
	 * @param obj
	 *            增加
	 * @return
	 */
	public boolean add(T obj) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tran = session.beginTransaction();
			session.save(obj);
			tran.commit();
			result = true;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 更新
	 */
	public boolean update(T object) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tran = session.beginTransaction();
			session.update(object);
			tran.commit();
			result = true;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * @param c
	 * @param id
	 *            根据逐渐获取实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(int id) {
		Session session = null;
		T object = null;
		try {
			session = HibernateUtil.getSession();
			object = (T) session.get(eClass, id);
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return object;
	}

	/**
	 * @param obj
	 *            删除
	 */
	public boolean delete(T obj) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tran = session.beginTransaction();
			session.delete(obj);
			tran.commit();
			result = true;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * @param <T>
	 *            获取list 必须select *
	 */
	@SuppressWarnings("unchecked")
	public List<T> query(String sql, String[] param) {

		List<T> list = new ArrayList<T>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(sql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					query.setString(i, param[i]);
				}
			}
			list = (List<T>) query.list();
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * 查询个别字段，返回List<Map> Map map = (Map)list.get[i];
	 * map.get("id");map.get("name");
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List queryList(String sql, String[] param) {
		List list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(sql).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					query.setString(i, param[i]);
				}
			}
			list = query.list();
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * @param sql
	 * @param param
	 *            获取一个实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T queryOne(String sql, String[] param) {
		T object = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(sql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					query.setString(0, param[i]);
				}
				object = (T) query.uniqueResult();
			}
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return object;
	}

	/**
	 * @param <T>
	 * @param sql
	 * @param param
	 * @param page
	 * @param size
	 * @获取分页list
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByPage(String sql, String[] param, int page, int size) {
		List<T> list = new ArrayList<T>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(sql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					query.setString(i, param[i]);
				}
			}
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			list = query.list();
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * @param hql
	 * @param pras
	 * @return 获取符合条件的数目
	 */
	public int getCount(String hql, String[] pras) {
		int resu = 0;
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Query q = s.createQuery(hql);
			if (pras != null) {
				for (int i = 0; i < pras.length; i++) {
					q.setString(i, pras[i]);
				}
			}
			resu = Integer.valueOf(q.iterate().next().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
		return resu;
	}
}