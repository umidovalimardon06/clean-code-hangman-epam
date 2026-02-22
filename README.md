# Unclean Code Branch

Bu branchda ataylab yomon yozilgan kod keltirilgan. Maqsad — xatolarni topib, refactor qilishni mashq qilish.

---

## Nimalar bor

**Application.java**
- Nested loop — keraksiz `O(n*m)` murakkablik
- Public fieldlar — encapsulation yo'q
- Funksiyalar bitta vazifa bajarmasligi
- String `==` bilan taqqoslash
- Mazmunsiz commentlar
- Noto'g'ri exception ishlatish
- Chalkash nomlash (o'zgaruvchi, metod nomlari)

**HangmanLogic.java**
- Encapsulation buzilgan
- Yana `==` bilan String taqqoslash
- DRY yo'q — bir xil kod qayta-qayta yozilgan
- Umumiy tuzilma juda murakkab va o'qish qiyin

---

## Maqsad

Shu xatolarni tushunish.

> Bu branch mergega mo'ljallanmagan — faqat o'quv maqsadida.