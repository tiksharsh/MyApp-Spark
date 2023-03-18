package example

object TraitsExample extends App {

    trait TraitA {
      def name = println("This is a grandparent class")
    }
    trait TraitB extends TraitA {
      override def name = {
        println("B is a Child of A")
        super.name
      }
    }
    trait TraitC extends TraitA {
      override def name = {
        println("C is a Child of A")
        super.name
      }
    }
    object grandChild extends TraitB with TraitC
    grandChild.name
}
