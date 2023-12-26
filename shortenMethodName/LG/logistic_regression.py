# Logistic Regression

import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier
from sklearn.linear_model import LogisticRegression, LinearRegression
import joblib
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LinearRegression
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import PolynomialFeatures
import seaborn as sns
from sklearn.linear_model import BayesianRidge, LinearRegression, ElasticNet
from sklearn.svm import SVR
from sklearn.ensemble import GradientBoostingRegressor
from sklearn.naive_bayes import GaussianNB
from sklearn.naive_bayes import BernoulliNB
from sklearn.naive_bayes import MultinomialNB


dataset = pd.read_csv('heuristicFirstTrainingData.csv')
X = dataset.iloc[:, [9,10,11,12,13,16,17,18,19,20,21,22]].values
y = dataset.iloc[:, 23].values

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.25, random_state = None)

# Feature Scaling

sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)


# 伯努利贝叶斯方法
 classifier = BernoulliNB()

classifier.fit(X_train, y_train)
joblib.dump(classifier,"forest.m")
