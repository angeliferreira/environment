<p align="center">
  <img src="https://github.com/angeliferreira/environment/blob/master/environment.jpg?raw=true"/>
</p>

# environment 
[![Build Status](https://travis-ci.org/angeliferreira/environment.png?branch=master)](https://travis-ci.org/angeliferreira/environment)
[![codecov](https://codecov.io/gh/angeliferreira/environment/branch/master/graphs/badge.svg)](https://codecov.io/gh/angeliferreira/environment)

Environment is a basic project for running set ups before running each Java automated test.


## Maven integration

To integrate *Environment* to your Maven project, you must declare the following dependency (Not in maven repository yet, must install it local):

```xml
<dependency>
    <groupId>br.com.lemao</groupId>
    <artifactId>environment</artifactId>
    <version>1.0</version>
    <scope>test</scope>
</dependency>
```

## Getting Started

## Basic Structure

### Creating your environment class

The abstract class *Environment* is the superclass for the Environments structure, where you gonna put your data creation calls.
The *@Environment* annotation is another way to declare your environment class as an Environment.

```java
public class SampleEnvironment extends Environment {

}
```
or
```java
@Environment
public class SampleEnvironment {

}
```

It supports 2 different basic usages, by class or by method.

### Structure of environment per class

The first implementation would provide one environment abstraction per class. You must implement the *run()* method only. If you are using *@Environment* annotation you must not use de *@Override* annotation on *run()*.

You have two auxiliary methods *beforeRun()* and *afterRun()* that are intended to provide creation and dispose of resources 
before and after executing the *run()* method, implementing these methods is optional, and the *afterRun()* method will be 
executed even if *run()* throws exception.

Your implementation, using the class Environment structure would be as follows:

```java
public class SampleEnvironment extends Environment {

   @Override
   public void run() {
      SampleUtil.createSample();
   }

}
```
or
```java
@Environment
public class SampleEnvironment {

   public void run() {
      SampleUtil.createSample();
   }

}
```

Your implementation using *beforeRun()* and *afterRun()* would be as follows:

```java
public class SampleEnvironment extends Environment {

    @Override
    public void run() {
        SampleUtil.createSample();
    }

    @Override
    public void afterRun() {
        SampleUtil.afterCreateSample();
    }

    @Override
    public void beforeRun() {
        SampleUtil.beforeCreateSample();
    }
    
}
```
or
```java
@Environment
@BeforeEnvironment("beforeRun")
@AfterEnvironment("afterRun")
public class SampleEnvironment {

    public void run() {
        SampleUtil.createSample();
    }

    public void afterRun() {
        SampleUtil.afterCreateSample();
    }

    public void beforeRun() {
        SampleUtil.beforeCreateSample();
    }
    
}
```

### Structure of environment per method

The second implementation would provide one environment abstraction per method. You must implement different methods as you will. In this case you must create an Environment class which comprises the related methods.

Your implementation, using the method Environment structure would be as follows:

```java
public class SampleEnvironment extends Environment {

   public void myEnvironmentMethodNameOneSample() {
      SampleUtil.createSample();
   }

   public void myEnvironmentMethodName2Samples() {
      SampleUtil.create2Samples();
   }

}
```

## Creating Environments hierarchically

It is possible to build a structure of hierarchical environments. You only need annotate the Environment method with *@GivenEnvironment* passing the Environment you want as a parent.

### @GivenEnvironment annotation

The use of the Environment is through *@GivenEnvironment* annotation. Its name was not given lightly, it was thought to lead naturally to the concept of *_[BDD](http://en.wikipedia.org/wiki/Behavior-driven_development)_* (_behavior driven development_) in which case it would be the abstraction of what you need to have as initial structure for your test.

The annotation *@GivenEnvironment* tells which structure will be executed before the test method. In the case of the annotation be in a test class, all tests that are NOT annotated with *@GivenEnvironment* or *@IgnoreEnvironment* will be executed after the execution of the Environment structure.

The GivenEnvironment annotation supports some basic uses.

#### Environment per class
You only have to pass as parameter the Environment class which has a run() method.

Its use in a test case would be as follows:

```java
public class Sample {

   @Test
   @GivenEnvironment(EnvironmentSample.class)
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```
or 
```java
@GivenEnvironment(EnvironmentSample.class)
public class Sample {

   @Test
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```

#### Environment per method
You only have to pass as parameter the Environment class and the Environment method.

Its use in a test case would be as follows:

```java
public class Sample {

   @Test
   @GivenEnvironment(value=EnvironmentSample.class, environmentName="myEnvironmentMethodNameOneSample")
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```
or
```java
@GivenEnvironment(value=EnvironmentSample.class, environmentName="myEnvironmentMethodNameOneSample")
public class Sample {

   @Test
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```

### @GivenEnvironments annotation

As *@GivenEnvironment*, the annotation *@GivenEnvironments* supports multiples *@GivenEnvironment*. It can be used to group Environments.

The GivenEnvironments annotation supports some basic uses.

#### Multiple Environments per class
You only have to pass as parameter the Environment classes which has a run() method.

Its use in a test case would be as follows:

```java
public class Sample {

   @Test
   @GivenEnvironments(environments = {
      @GivenEnvironment(EnvironmentSample.class),
      @GivenEnvironment(EnvironmentAnotherSample.class)
   })
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```
or 
```java
@GivenEnvironments(environments = {
   @GivenEnvironment(EnvironmentSample.class),
   @GivenEnvironment(EnvironmentAnotherSample.class)
})
public class Sample {

   @Test
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```

#### Multiple Environments per method
You only have to pass as parameter the Environment classes and the Environment methods.

Its use in a test case would be as follows:

```java
public class Sample {

   @Test
   @GivenEnvironments(environments = {
      @GivenEnvironment(value=EnvironmentSample.class, environmentName="myEnvironmentMethodNameOneSample"),
      @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
   })
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```
or
```java
@GivenEnvironments(environments = {
   @GivenEnvironment(value=EnvironmentSample.class, environmentName="myEnvironmentMethodNameOneSample"),
   @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
})
public class Sample {

   @Test
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```

#### Hybrid Multiple Environments
You can pass as parameter either Environments per class or Environmets per method.

Its use in a test case would be as follows:

```java
public class Sample {

   @Test
   @GivenEnvironments(environments = {
      @GivenEnvironment(EnvironmentSample.class),
      @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
   })
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```
or
```java
@GivenEnvironments(environments = {
   @GivenEnvironment(EnvironmentSample.class),
   @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
})
public class Sample {

   @Test
   public void method() {
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```

#### Group Multiple Environments
You can pass as parameter either Environments per class or Environmets per method.

Its use in a test case would be as follows:

```java
@GivenEnvironments(environments = {
   @GivenEnvironment(EnvironmentSample.class),
   @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
})
public class AgregateSampleEnvironment extends Environment {

}
```
or
```java
public class AgregateSampleEnvironment extends Environment {

   @GivenEnvironments(environments = {
      @GivenEnvironment(EnvironmentSample.class),
      @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
   })
   @Override
   public void run() {}
}
```
or
```java
@Environment
@GivenEnvironments(environments = {
   @GivenEnvironment(EnvironmentSample.class),
   @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
})
public class AgregateSampleEnvironment {

}
```
or
```java
@Environment
public class AgregateSampleEnvironment {

   @GivenEnvironments(environments = {
      @GivenEnvironment(EnvironmentSample.class),
      @GivenEnvironment(value=EnvironmentAnotherSample.class, environmentName="myEnvironmentMethodNameAnotherSample")
   })
   public void run() {}
}
```

## Executing your Environments

The only thing you have to do is get an EnvironmentExecutor and execute!

```java
public class Sample {

   @Test
   public void method() {
      //It expects the method EnvironmentSample.run() to be implemented
      EnvironmentExecutor.gimme().execute(EnvironmentSample.class);
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }
   
   @Test
   public void method1() {
      //It expects to exists the method EnvironmentSample.environmentSampleMethodName()
      EnvironmentExecutor.gimme().execute(EnvironmentSample.class, "environmentSampleMethodName");
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }
   
   @Test
   public void method2() {
      //It expects the method EnvironmentSample.run() and EnvironmentAnotherSample.run() to be implemented
      EnvironmentExecutor.gimme().execute(EnvironmentSample.class, EnvironmentAnotherSample.class);
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }
   
   @Test
   public void method3() {
      GivenEnvironment environmentSample = GivenEnvironmentBuilder.getInstance()
         .forClass(EnvironmentSample.class)
         .gimme();
      GivenEnvironment environmentAnotherSample = GivenEnvironmentBuilder.getInstance()
          .forClass(EnvironmentAnotherSample.class)
          .gimme();
      EnvironmentExecutor.gimme().execute(environmentSample, environmentAnotherSample);
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }
   
   @Test
   public void method4() {
      GivenEnvironment environmentAnotherSample = GivenEnvironmentBuilder.getInstance()
          .forClass(EnvironmentAnotherSample.class)
          .gimme();
      EnvironmentExecutor.gimme().execute(EnvironmentSample.class, environmentAnotherSample);
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }
   
   @Test
   public void method5() {
      GivenEnvironment environmentAnotherSample = GivenEnvironmentBuilder.getInstance()
          .forClass(EnvironmentAnotherSample.class)
	  .forName("environmentAnotherSampleMethodName")
          .gimme();
      EnvironmentExecutor.gimme().execute(EnvironmentSample.class, environmentAnotherSample);
      org.junit.Assert.assertFalse(SampleUtil.findAll().isEmpty());
   }

}
```

## You must also see

REMEMBER, TESTS ARE DOCUMENTATION

* *_[Environment Example Tests](https://github.com/angeliferreira/environment/tree/master/src/test/java/br/com/lemao/environment/test)_*
* *_[Environment JUnit](https://github.com/angeliferreira/environment-junit)_*
* *_[Environment Spring](https://github.com/angeliferreira/environment-spring)_*
* *_[SimulaTest - Simulatest Test Harness Framework](https://github.com/gabrielsuch/simulatest)_*
* *_[Fixture Factory](https://github.com/six2six/fixture-factory)_*
* *_[DbUnit](http://dbunit.sourceforge.net/)_*
