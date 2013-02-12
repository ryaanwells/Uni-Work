from django.db import models
from django import forms

class Category(models.Model):
    name = models.CharField(max_length=128,unique=True)
    
    def __unicode__(self):
        return self.name

class Page(models.Model):
    category = models.ForeignKey(Category)
    title = models.CharField(max_length=128)
    url = models.URLField()
    views = models.IntegerField()
    
    def __unicode__(self):
        return self.title

# create a form for Category and Page
class CategoryForm(forms.ModelForm):
        name = forms.CharField(max_length=50,
                help_text='Please enter the name of the category.')
        class Meta:
                # associate the model, Category, with the ModelForm
                model = Category

class PageForm(forms.ModelForm):
        title = forms.CharField(max_length=100,
                help_text='Please enter the title of the page.')
        url = forms.CharField(max_length=200,
                help_text='Please enter the URL of the page.')
        class Meta:
                model = Page
                # select which fields will be present on the form
                # (i.e. we are hiding the foreign key and views fields)
                fields = ('title','url')
        
        def clean(self):
            cleaned_data = self.cleaned_data
            url = cleaned_data.get('url')
            if not url.startswith('http://'):
                url = 'http://' + url
            cleaned_data['url'] = url
            return cleaned_data
