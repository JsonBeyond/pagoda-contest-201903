package com.pagoda.hdtt.aotogen;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("hd_order", "id", HdOrder.class);
		arp.addMapping("msg_template", "templateID", MsgTemplate.class);
		// Composite Primary Key order: delete,id
		arp.addMapping("question", "delete,id", Question.class);
		arp.addMapping("user", "id", User.class);
	}
}

