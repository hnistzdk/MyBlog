package com.zdk.MyBlog.utils;

import com.zdk.MyBlog.controller.CommonController;
import com.zdk.MyBlog.model.pojo.auth.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zdk
 * @date 2021/10/31 15:01
 */
@Component
public class TreeUtil extends CommonController {
    /**
     * 转换为Model tree
     * @param allList
     * @param checkIsParentFunc
     * @return
     */
    public List<Role> convertToModelTree(List<Role> allList, TreeCheckIsParentNode<Role> checkIsParentFunc) {
        if(notOk(allList)) {return null;}
        List<Role> parents= new ArrayList<>();
        Role m;
        for(int i=0;i<allList.size();i++) {
            m=allList.get(i);
            m.setItems(new HashMap<>(16));
            if(checkIsParentFunc.isParent(m)) {
                parents.add(m);
                allList.remove(m);
                i--;
            }
        }
        if(parents.size()>0&&allList.size()>0) {
            processTreeNodes(allList,parents);
        }
        return parents;
    }

    /**
     * 处理tree节点
     * @param allList
     * @param parents
     */
    public void processTreeNodes(List<Role> allList, List<Role> parents) {
        ListMap<String, Role> map= new ListMap<>();
        for(Role m:allList) {
            map.addItem("p_"+m.getPid(), m);
        }
        for(Role p:parents) {
            processTreeSubNodes(map,p);
        }

    }

    /**
     * 递归处理tree子节点
     * @param map
     * @param m
     */
    public void processTreeSubNodes(ListMap<String, Role> map, Role m) {
        List<Role> items=map.get("p_"+m.getId());
        if(items!=null&&items.size()>0) {
            for(Role item:items) {
                processTreeSubNodes(map, item);
            }
        }
        m.getItems().put("item",items);
    }
}

class ListMap<K,T> extends HashMap<K,List<T>> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ListMap(){
        super();
    }

    /**
     * 添加实体
     * @param key
     * @param value
     */

    public void addItem(K key,T value){
        List<T> l = get(key);
        if(l==null){
            l = new ArrayList<T>();
            l.add(value);
            put(key,l);
        }else{
            l.add(value);
        }
    }


}
