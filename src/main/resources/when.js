when = {
    // A&(B|C|D) | (E&F)
    // a&b | a&c | a &d | e&f
    // (A∣B)&(A∣C)&(A∣D)&(B∣C∣D)
    // a&(b|(c|(d|f&d)))|e
    "type": "When",
    "branches": [
        {
            "type": "Branch",
            // 每个predicate之间的关系是or
            "ors": [
                {
                    "type": "And",
                    "ands": [
                        // a
                        {
                            "type": "isGreaterThen",
                            "items": [
                                {
                                    "type": "Field",
                                    "key": "amount"
                                },
                                {
                                    "type": "Const",
                                    "value": "1000"
                                }
                            ]
                        },
                        {
                            "type": "Ors",
                            "ors": [
                                {
                                    // b
                                },
                                {
                                    "type": "Ors",
                                    "ors": [
                                        {
                                            // c
                                        },
                                        {
                                            "type": "Ors",
                                            "ors": [
                                                {
                                                    // d
                                                },
                                                {
                                                    "type": "Ands",
                                                    "ands": [
                                                        {
                                                            // f
                                                        },
                                                        {
                                                            // d
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                },
                // or
                {
                    // e
                    "type": "isNotIn",
                    "items": [
                        {
                            "type": "Field",
                            "key": "time"
                        },
                        {
                            "type": "Const",
                            "value": "00:00-08:00"
                        }
                    ]
                },
                {
                    "type": "equals",
                    "items": [
                        {
                            "type": "Field",
                            "key": "time"
                        },
                        {
                            "type": "Const",
                            "value": "00:00-08:00"
                        }
                    ]
                }
            ],
            "then": {
                "type": "When",


            }
        },
        {
            "type": "Branch",
            // 每个predicate之间的关系是or
            "ors": [
                {
                    "type": "isGreaterThen",
                    "items": [
                        {
                            "type": "StreamCubeFeature",
                            "ns": "BANK",
                            "dim": "卡号",
                            "name": "同一卡号近x天交易金额总和",
                            "udf": "5d7216en4160",
                            "key": {
                                "type": "Field",
                                "key": "card_no"
                            },
                            "ref": {
                                "type": "Field",
                                "value": "time"
                            },
                            "dur": {
                                "type": "Const",
                                "value": "1d"
                            },
                            "current": {
                                "type": "EventObject"
                            }

                        },
                        {
                            "type": "Const",
                            "value": "1000"
                        }
                    ]
                }
            ],
            "then": {
                "type": "When",
                "branches": []
            }
        }
    ]
}