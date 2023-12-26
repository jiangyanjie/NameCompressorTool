import sys
import joblib


if __name__ == '__main__':
    # print("load model ...")
    model = joblib.load("D:\\Abbreviations\\logicRegression\\logisticregression\\heuristicFirst-3.m")
    values=list()
    # print(values)
    # print(sys.argv)
    # for i in range(len(sys.argv)):
    #     if i==0:
    #         continue
    #     value.append(float(sys.argv[i]))
    values.append([float(x) for x in sys.argv[1].split(' ')])
    predict = model.predict_proba(values)
    # predict = model.predict(values)
    print(predict)


