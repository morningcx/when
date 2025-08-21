function main() {
    return {
        "type": "Equals",
        "items": [
            {
                "type": "IsNotIn",
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
                            "key": "time"
                        },
                        "dur": {
                            "type": "Const",
                            "value": "1d"
                        },
                        "current": {
                            "type": "Const",
                            "value": "current"
                        }
                    },
                    {
                        "type": "Const",
                        "value": "00:00-08:00"
                    }
                ]
            },
            {
                "type": "Equals",
                "items": [
                    {
                        "type": "Field",
                        "key": "time"
                    },
                    {
                        "type": "Const",
                        "value": "12:00"
                    }
                ]
            },
            {
                "type": "IsGreaterThan",
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
                            "key": "time"
                        },
                        "dur": {
                            "type": "Const",
                            "value": "1d"
                        },
                        "current": {
                            "type": "Const",
                            "value": "current"
                        }

                    },
                    {
                        "type": "Const",
                        "value": "1000"
                    }
                ]
            }
        ]
    };
}


