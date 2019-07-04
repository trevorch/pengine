define({ "api": [
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./docs/main.js",
    "group": "D__Workspace_Java_pengine_docs_main_js",
    "groupTitle": "D__Workspace_Java_pengine_docs_main_js",
    "name": ""
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./template/main.js",
    "group": "D__Workspace_Java_pengine_template_main_js",
    "groupTitle": "D__Workspace_Java_pengine_template_main_js",
    "name": ""
  },
  {
    "type": "post",
    "url": "/promotion/best",
    "title": "4.最佳促销活动",
    "description": "<p>获取订单的最优惠的促销活动</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/best"
      }
    ],
    "name": "bestResult",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "RequestBody",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "样例参数:",
          "content": "{\n   \"no\":\"98238179849\",\n   \"items\":[\n     {\n       \"id\": \"8139349\",\n       \"name\": \"伊利金典有机纯牛奶\",\n       \"count\": 100,\n       \"price\": 71.90\n     },\n     {\n       \"id\": \"1599047\",\n       \"name\": \"鲁花花生油\",\n       \"count\": 1,\n       \"price\": 165.90\n     },\n     {\n       \"id\": \"2828950\",\n       \"name\": \"维达抽纸\",\n       \"count\": 10,\n       \"price\": 61.90\n     }\n   ]\n }",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": {\n        \"finalPrice\": 2627.94,\n        \"gifts\": [],\n        \"msg\": \"订单【98238179849】总价【7974.9】满足【鲁花花生油满2瓶打5折且总价满1000打6折】,最终价格【2627.94】\",\n        \"ok\": true,\n        \"order\": {\n            \"items\": [\n                {\n                    \"count\": 100,\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\",\n                    \"price\": 71.9\n                },\n                {\n                    \"count\": 1,\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\",\n                    \"price\": 165.9\n                },\n                {\n                    \"count\": 10,\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\",\n                    \"price\": 61.9\n                }\n            ],\n            \"no\": \"98238179849\",\n            \"totalPrice\": 7974.9\n        },\n        \"promotion\": {\n            \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n            \"close\": 1572019200000,\n            \"desc\": \"鲁花花生油满2瓶打5折且总价满1000打6折\",\n            \"id\": \"DIS002\",\n            \"map\": {\n                \"1\": {\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\"\n                },\n                \"2\": {\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\"\n                },\n                \"3\": {\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\"\n                }\n            },\n            \"name\": \"满1000打6折\",\n            \"rule\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000\",\n            \"sequence\": 2,\n            \"start\": 1561651200000\n        },\n        \"promotionType\": \"discount\",\n        \"saving\": 5346.96,\n        \"savingPercent\": 67\n    },\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "错误响应:",
          "content": "{\n    \"code\": \"Failure\",\n    \"msg\": \"找不到符合条件的促销\",\n    \"ok\": false\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/OrderController.java",
    "groupTitle": "Promotion"
  },
  {
    "type": "post",
    "url": "/promotion/best",
    "title": "订单最佳促销",
    "version": "0.1.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/best"
      }
    ],
    "name": "bestResult",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "RequestBody",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "样例参数:",
          "content": "{\n   \"no\":\"98238179849\",\n   \"items\":[\n     {\n       \"id\": \"8139349\",\n       \"name\": \"伊利金典有机纯牛奶\",\n       \"count\": 100,\n       \"price\": 71.90\n     },\n     {\n       \"id\": \"1599047\",\n       \"name\": \"鲁花花生油\",\n       \"count\": 1,\n       \"price\": 165.90\n     },\n     {\n       \"id\": \"2828950\",\n       \"name\": \"维达抽纸\",\n       \"count\": 10,\n       \"price\": 61.90\n     }\n   ]\n }",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": {\n        \"finalPrice\": 2627.94,\n        \"gifts\": [],\n        \"msg\": \"订单【98238179849】总价【7974.9】满足【鲁花花生油满2瓶打5折且总价满100打6折】,最终价格【2627.94】\",\n        \"ok\": true,\n        \"order\": {\n            \"items\": [\n                {\n                    \"count\": 100,\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\",\n                    \"price\": 71.9\n                },\n                {\n                    \"count\": 1,\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\",\n                    \"price\": 165.9\n                },\n                {\n                    \"count\": 10,\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\",\n                    \"price\": 61.9\n                }\n            ],\n            \"no\": \"98238179849\",\n            \"totalPrice\": 7974.9\n        },\n        \"promotion\": {\n            \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n            \"close\": 1572019200000,\n            \"desc\": \"鲁花花生油满2瓶打5折且总价满100打6折\",\n            \"id\": \"DIS002\",\n            \"map\": {\n                \"1\": {\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\"\n                },\n                \"2\": {\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\"\n                },\n                \"3\": {\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\"\n                }\n            },\n            \"name\": \"满1000打6折\",\n            \"rule\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 100\",\n            \"sequence\": 2,\n            \"start\": 1561651200000\n        },\n        \"promotionType\": \"discount\",\n        \"saving\": 5346.96,\n        \"savingPercent\": 67\n    },\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/doc/v010.js",
    "groupTitle": "Promotion"
  },
  {
    "type": "post",
    "url": "/promotion/create",
    "title": "1.创建促销活动",
    "description": "<p>如果指定的促销活动ID在系统中已经存在，则新促销活动会覆盖系统中已经存在的促销活动</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/create"
      }
    ],
    "name": "create",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "RequestBody",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "样例参数:",
          "content": " {\n  \"id\": \"DIS001\",\n  \"name\": \"满50000打6折\",\n  \"desc\": \"满50000打6折\",\n  \"sequence\": 2,\n  \"start\": \"2019-06-28 00:00:00\",\n  \"close\": \"2019-10-26 00:00:00\",\n  \"condition\": \"false (c1*p1+c2*p2+c3*p3) >= 500\",\n  \"action\": \"discount (c1* p1+ c 2 *p 2+c3*p3)*0.6\",\n  \"map\": {\n    \"1\":{\n      \"id\": \"8139349\",\n      \"name\": \"伊利金典有机纯牛奶\"\n    },\n    \"2\":{\n      \"id\": \"1599047\",\n      \"name\": \"鲁花花生油\"\n    },\n    \"3\":{\n      \"id\": \"2828950\",\n      \"name\": \"维达抽纸\"\n    }\n  }\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"success\",\n    \"msg\": \"创建成功\",\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/CreateController.java",
    "groupTitle": "Promotion"
  },
  {
    "type": "post",
    "url": "/promotion/create",
    "title": "创建促销",
    "version": "0.1.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/create"
      }
    ],
    "name": "create",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "RequestBody",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "样例参数:",
          "content": " {\n  \"id\": \"DIS002\",\n  \"name\": \"满1000打6折\",\n  \"desc\": \"鲁花花生油满2瓶打5折且总价满1000打6折\",\n  \"sequence\": 2,\n  \"start\": \"2019-06-28 00:00:00\",\n  \"close\": \"2019-10-26 00:00:00\",\n  \"rule\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000\",\n  \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n  \"map\": {\n    \"1\":{\n      \"id\": \"8139349\",\n      \"name\": \"伊利金典有机纯牛奶\"\n    },\n    \"2\":{\n      \"id\": \"1599047\",\n      \"name\": \"鲁花花生油\"\n    },\n    \"3\":{\n      \"id\": \"2828950\",\n      \"name\": \"维达抽纸\"\n    }\n  }\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"success\",\n    \"msg\": \"创建成功\",\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/doc/v010.js",
    "groupTitle": "Promotion"
  },
  {
    "type": "get",
    "url": "/promotion/get-by-prod/:productId",
    "title": "5.商品促销活动",
    "description": "<p>根据商品ID取一个排序最靠前的促销活动，所获取的促销活动信息可用于显示在商品展示信息上</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/get-by-prod/:productId"
      }
    ],
    "name": "get_by_prod",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "string",
            "optional": false,
            "field": "productId",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": {\n        \"action\": \"giving 2828950:2\",\n        \"close\": 1572019200000,\n        \"desc\": \"满700送纸巾\",\n        \"id\": \"GIV001\",\n        \"map\": {\n            \"1\": {\n                \"id\": \"8139349\",\n                \"name\": \"伊利金典有机纯牛奶\"\n            },\n            \"2\": {\n                \"id\": \"1599047\",\n                \"name\": \"鲁花花生油\"\n            },\n            \"3\": {\n                \"id\": \"2828950\",\n                \"name\": \"维达抽纸\"\n            }\n        },\n        \"name\": \"满700送1提纸巾\",\n        \"rule\": \"(c1*p1+c2*p2+c3*p3) >= 700\",\n        \"sequence\": 0,\n        \"start\": 1561651200000\n    },\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "错误响应:",
          "content": "{\n    \"code\": \"Failure\",\n    \"msg\": \"此商品没有任何促销活动\",\n    \"ok\": false\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/ProductController.java",
    "groupTitle": "Promotion"
  },
  {
    "type": "post",
    "url": "/promotion/list",
    "title": "2.促销活动列表",
    "description": "<p>获取所有有效的促销活动，以列表形式返回</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/list"
      }
    ],
    "name": "list",
    "group": "Promotion",
    "parameter": {
      "examples": [
        {
          "title": "样例参数:",
          "content": "无需参数",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": [\n        {\n            \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n            \"close\": 1572019200000,\n            \"desc\": \"鲁花花生油满2瓶打5折且总价满1000打6折\",\n            \"id\": \"DIS002\",\n            \"map\": {\n                \"1\": {\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\"\n                },\n                \"2\": {\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\"\n                },\n                \"3\": {\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\"\n                }\n            },\n            \"name\": \"满1000打6折\",\n            \"rule\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000\",\n            \"sequence\": 2,\n            \"start\": 1561651200000\n        }\n    ],\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/ListController.java",
    "groupTitle": "Promotion"
  },
  {
    "type": "post",
    "url": "/promotion/list",
    "title": "促销列表",
    "version": "0.1.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/list"
      }
    ],
    "name": "list",
    "group": "Promotion",
    "parameter": {
      "examples": [
        {
          "title": "样例参数:",
          "content": "无需参数",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": [\n        {\n            \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n            \"close\": 1572019200000,\n            \"desc\": \"鲁花花生油满2瓶打5折且总价满100打6折\",\n            \"id\": \"DIS002\",\n            \"map\": {\n                \"1\": {\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\"\n                },\n                \"2\": {\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\"\n                },\n                \"3\": {\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\"\n                }\n            },\n            \"name\": \"满1000打6折\",\n            \"rule\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 100\",\n            \"sequence\": 2,\n            \"start\": 1561651200000\n        }\n    ],\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/doc/v010.js",
    "groupTitle": "Promotion"
  },
  {
    "type": "get",
    "url": "/promotion/list-by-prod/:productId",
    "title": "6.商品促销列表",
    "description": "<p>根据商品ID获取此商品参与的所有促销活动</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/list-by-prod/:productId"
      }
    ],
    "name": "list_by_prod",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "string",
            "optional": false,
            "field": "productId",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": [\n        {\n            \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n            \"close\": 1572019200000,\n            \"desc\": \"鲁花花生油满2瓶打5折且总价满1000打6折\",\n            \"id\": \"DIS002\",\n            \"map\": {\n                \"1\": {\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\"\n                },\n                \"2\": {\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\"\n                },\n                \"3\": {\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\"\n                }\n            },\n            \"name\": \"满1000打6折\",\n            \"rule\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000\",\n            \"sequence\": 2,\n            \"start\": 1561651200000\n        }\n    ],\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/ProductController.java",
    "groupTitle": "Promotion"
  },
  {
    "type": "post",
    "url": "/promotion/validate",
    "title": "3.验证促销活动",
    "description": "<p>验证一个订单是否满足所指定的促销活动</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/validate"
      }
    ],
    "name": "validate",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "RequestBody",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "样例参数:",
          "content": " {\n\t\"order\":  {\n    \"no\":\"98238179849\",\n    \"items\":[\n      {\n        \"id\": \"8139349\",\n        \"name\": \"伊利金典有机纯牛奶\",\n        \"count\": 100,\n        \"price\": 71.90\n      },\n      {\n        \"id\": \"1599047\",\n        \"name\": \"鲁花花生油\",\n        \"count\": 1,\n        \"price\": 165.90\n      },\n      {\n        \"id\": \"2828950\",\n        \"name\": \"维达抽纸\",\n        \"count\": 10,\n        \"price\": 61.90\n      }\n    ]\n  },\n  \"promotionId\":\"DIS002\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": {\n        \"afterPrice\": 2627.94,\n        \"commonPrice\": 7974.9,\n        \"extraPrice\": 0,\n        \"finalPrice\": 2627.94,\n        \"gifts\": [],\n        \"msg\": \"订单[98238179849],总价[7974.9]=非活动[0]+活动[7974.9],活动部分满足[满1000打6折],最终价[2627.94]\",\n        \"ok\": true,\n        \"order\": {\n            \"items\": [\n                {\n                    \"count\": 100,\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\",\n                    \"price\": 71.9\n                },\n                {\n                    \"count\": 1,\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\",\n                    \"price\": 165.9\n                },\n                {\n                    \"count\": 10,\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\",\n                    \"price\": 61.9\n                }\n            ],\n            \"no\": \"98238179849\",\n            \"totalPrice\": 7974.9\n        },\n        \"promotion\": {\n            \"action\": \"discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6\",\n            \"close\": 1572019200000,\n            \"condition\": \"false (c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000\",\n            \"desc\": \"鲁花花生油满2瓶打5折且总价满1000打6折\",\n            \"id\": \"DIS002\",\n            \"map\": {\n                \"1\": {\n                    \"id\": \"8139349\",\n                    \"name\": \"伊利金典有机纯牛奶\"\n                },\n                \"2\": {\n                    \"id\": \"1599047\",\n                    \"name\": \"鲁花花生油\"\n                },\n                \"3\": {\n                    \"id\": \"2828950\",\n                    \"name\": \"维达抽纸\"\n                }\n            },\n            \"name\": \"满1000打6折\",\n            \"rule\": {\n                \"exactMatch\": false,\n                \"expression\": \"(c1>=2)&&(c1*p1+c2*p2+c3*p3)>=1000\"\n            },\n            \"sequence\": 3,\n            \"start\": 1561651200000\n        },\n        \"promotionType\": \"discount\",\n        \"saving\": 5346.96,\n        \"savingPercent\": 67\n    },\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/ValidateController.java",
    "groupTitle": "Promotion"
  },
  {
    "type": "get",
    "url": "/promotion/virtual/:promotionId",
    "title": "7.促销虚拟商品",
    "description": "<p>根据促销ID，取回一个促销虚拟商品，此商品属性是真正商品属性的子集，可显示在真正商品可显示的UI位置</p>",
    "version": "0.2.0",
    "sampleRequest": [
      {
        "url": "http://120.76.101.151:8080/promotion/virtual/:promotionId"
      }
    ],
    "name": "virtualProduct",
    "group": "Promotion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "string",
            "optional": false,
            "field": "promotionId",
            "description": ""
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "成功响应:",
          "content": "{\n    \"code\": \"Success\",\n    \"msg\": {\n        \"desc\": \"满700送纸巾\",\n        \"id\": \"PROM-GIV001\",\n        \"items\": [\n            {\n                \"id\": \"8139349\",\n                \"name\": \"伊利金典有机纯牛奶\"\n            },\n            {\n                \"id\": \"1599047\",\n                \"name\": \"鲁花花生油\"\n            },\n            {\n                \"id\": \"2828950\",\n                \"name\": \"维达抽纸\"\n            }\n        ],\n        \"name\": \"满700送1提纸巾\"\n    },\n    \"ok\": true\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "错误响应:",
          "content": "{\n    \"code\": \"Failure\",\n    \"msg\": \"促销[promotionId=55]不存在\",\n    \"ok\": false\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./pengine-api/src/main/java/org/bizboost/pengine/controller/promotion/ProductController.java",
    "groupTitle": "Promotion"
  }
] });
